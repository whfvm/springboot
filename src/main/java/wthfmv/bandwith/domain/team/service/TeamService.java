package wthfmv.bandwith.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.team.dto.req.Create;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;
import wthfmv.bandwith.domain.teamMember.entity.Position;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;
import wthfmv.bandwith.domain.teamMember.repository.TeamMemberRepository;
import wthfmv.bandwith.global.security.jwt.JwtProvider;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetails;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void create(Create create) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        Team team = new Team(
                create.getName(),
                create.getLimitMember(),
                create.getProfileImage()
        );

        teamRepository.save(team);

        TeamMember teamMember = new TeamMember(Position.LEADER, member, team);

        teamMemberRepository.save(teamMember);
    }

    @Transactional
    public void delete(String search) {
        teamRepository.deleteById(UUID.fromString(search));
    }

    @Transactional
    public String publish(String bandID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        return jwtProvider.createParticipationCode(UUID.fromString(bandID));
    }

    @Transactional
    public void sign(String code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        String teamID = jwtProvider.getID(code);

        Team team = teamRepository.findById(UUID.fromString(teamID)).orElseThrow(
                () -> new RuntimeException("해당 팀 없음")
        );

        if(teamMemberRepository.findByMemberAndTeam(member, team).isPresent()){
            throw new RuntimeException("이미 가입된 멤버입니다.");
        } else {
           teamMemberRepository.save(new TeamMember(Position.MEMBER, member, team));
        }
    }
}
