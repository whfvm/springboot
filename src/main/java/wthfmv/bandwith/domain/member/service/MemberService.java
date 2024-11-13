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
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public TokenRes googleOAuth(String id) {
        Optional<Member> optionalMember = memberRepository.findByProviderAndProviderId("google", id);

        if(optionalMember.isPresent()){
            // 이미 가입한 유저면
            Member member = optionalMember.get();

            String accessToken = jwtProvider.createAccessToken(member.getId());
            String refreshToken = jwtProvider.createRefreshToken();

            long count = refreshTokenRepository.countByMember(member);

            if (count > 4) {
                RefreshToken oldestToken = refreshTokenRepository.findFirstByMemberOrderByCreatedAtAsc(member);
                refreshTokenRepository.delete(oldestToken);
            }

            RefreshToken refreshTokenObject = new RefreshToken(refreshToken, member, LocalDateTime.now());
            refreshTokenRepository.save(refreshTokenObject);

            return new TokenRes(accessToken, refreshToken, true);
        } else {
            // 가입하지 않은 유저면
            Member newMember = new Member("google", id);

            memberRepository.save(newMember);

            String accessToken = jwtProvider.createAccessToken(newMember.getId());
            String refreshToken = jwtProvider.createRefreshToken();

            RefreshToken refreshTokenObject = new RefreshToken(refreshToken, newMember, LocalDateTime.now());
            refreshTokenRepository.save(refreshTokenObject);

            return new TokenRes(accessToken, refreshToken, false);
        }
    }

    public MemberRes member(UUID uuid) {
        Member member = memberRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException(uuid.toString() + "멤버 없음")
        );

        return new MemberRes(member);
    }

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
}
