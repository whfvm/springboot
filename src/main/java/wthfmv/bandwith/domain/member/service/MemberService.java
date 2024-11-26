package wthfmv.bandwith.domain.member.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.member.dto.req.MemberUpdateReq;
import wthfmv.bandwith.domain.member.dto.res.MemberRes;
import wthfmv.bandwith.domain.member.dto.res.TokenRes;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.refreshToken.entity.RefreshToken;
import wthfmv.bandwith.domain.refreshToken.repository.RefreshTokenRepository;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;
import wthfmv.bandwith.domain.teamMember.entity.Position;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;
import wthfmv.bandwith.domain.teamMember.repository.TeamMemberRepository;
import wthfmv.bandwith.global.security.jwt.JwtProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;


    public MemberRes member(UUID uuid) {
        Member member = memberRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException(uuid.toString() + "멤버 없음")
        );

        return new MemberRes(member);
    }

    @Transactional
    public void update(UUID uuid, MemberUpdateReq memberUpdateReq) {
        Member member = memberRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException(uuid + "멤버 없음")
        );

        member.update(memberUpdateReq);

        List<TeamMember> byMember = teamMemberRepository.findByMember(uuid);

        if(byMember.isEmpty()){
            Team team = new Team(memberUpdateReq.getName() + "의 개인밴드", 1, null);
            teamRepository.save(team);
            teamMemberRepository.save(new TeamMember(Position.LEADER, member, team, null));
        }
    }

    @Transactional
    public void delete(UUID uuid) {
        if(teamMemberRepository.existsByPositionAndMemberId(Position.LEADER, uuid)){
            throw new RuntimeException("리더인 밴드를 탈퇴, 삭제해 주세요");
        }
        memberRepository.deleteById(uuid);
    }
}
