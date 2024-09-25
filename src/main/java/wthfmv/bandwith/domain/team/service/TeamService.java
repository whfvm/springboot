package wthfmv.bandwith.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.team.dto.req.TeamCreate;
import wthfmv.bandwith.domain.team.dto.res.TeamListRes;
import wthfmv.bandwith.domain.team.dto.res.TeamRes;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;
import wthfmv.bandwith.domain.teamMember.entity.Position;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;
import wthfmv.bandwith.domain.teamMember.repository.TeamMemberRepository;
import wthfmv.bandwith.global.security.jwt.JwtProvider;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    private final Map<String, String> joinCode = new HashMap<>();

    @Transactional
    public void create(TeamCreate teamCreate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        Team team = new Team(teamCreate);

        Team savedTeam = teamRepository.save(team);

        TeamMember teamMember = new TeamMember(Position.LEADER, member, savedTeam, null);

        teamMemberRepository.save(teamMember);
    }

    @Transactional
    public void delete(String search) {
        teamRepository.deleteById(UUID.fromString(search));
    }

    @Transactional
    public String publish(String teamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if(teamMemberRepository.existsByPositionAndTeamIdAndMemberId(Position.LEADER, UUID.fromString(teamId),customUserDetails.getUuid())){
            String randomCode = RandomStringUtils.random(6, true, true);
            joinCode.put(randomCode, teamId);

            return randomCode;
        }

        return "XXXXXX";
    }

    @Transactional
    public void sign(String code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = memberRepository.findById(customUserDetails.getUuid()).orElseThrow(
                () -> new RuntimeException(customUserDetails.getUuid() + "멤버 없음")
        );

        String teamId = joinCode.get(code);
        joinCode.remove(code);

        Team team = teamRepository.findById(UUID.fromString(teamId)).orElseThrow(
                () -> new RuntimeException("해당 팀 없음")
        );

        if(teamMemberRepository.findByMemberAndTeam(member, team).isPresent()){
            throw new RuntimeException("이미 가입된 멤버입니다.");
        } else {
           teamMemberRepository.save(new TeamMember(Position.MEMBER, member, team, null));
        }
    }

    @Transactional(readOnly = true)
    public TeamRes team(String teamId) {
        Team team = teamRepository.findById(UUID.fromString(teamId)).orElseThrow(
                () -> new RuntimeException("해당 팀 없음")
        );

        return new TeamRes(team);
    }

    @Transactional(readOnly = true)
    public List<TeamListRes> list(String userUUID) {
        List<TeamMember> teamMembers = teamMemberRepository.findByMember(UUID.fromString(userUUID));

        return teamMembers.stream()
                .map(TeamListRes::new)
                .collect(Collectors.toList());
    }
}
