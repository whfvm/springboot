package wthfmv.bandwith.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wthfmv.bandwith.domain.member.dto.req.LocalLoginReq;
import wthfmv.bandwith.domain.member.dto.res.TokenRes;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.member.repository.MemberRepository;
import wthfmv.bandwith.domain.refreshToken.entity.RefreshToken;
import wthfmv.bandwith.domain.refreshToken.repository.RefreshTokenRepository;
import wthfmv.bandwith.global.security.jwt.JwtProvider;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenRes googleOAuth(String email, String name) {
        Optional<Member> optionalMember = memberRepository.findByEmailAndProvider(email, "google");

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return generateTokensForMember(member, true);
        } else {
            Member newMember = new Member("google", email,name);
            memberRepository.save(newMember);
            return generateTokensForMember(newMember, false);
        }
    }

    @Transactional
    public TokenRes localAuth(LocalLoginReq localLoginReq){
        Optional<Member> optionalMember = memberRepository.findByEmailAndProvider(localLoginReq.getEmail(), "local");

        // 존재한다면 로그인
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            if(passwordEncoder.matches(localLoginReq.getPassword(), member.getPassword())){
                return generateTokensForMember(member, true);
            } else {
                throw new RuntimeException("옳지 않은 비밀번호");
            }
        } else {
            // 존재하지 않는다면 새로 만들어서 로그인
            Member newMember = new Member(localLoginReq, "local", passwordEncoder.encode(localLoginReq.getPassword()));
            memberRepository.save(newMember);
            return generateTokensForMember(newMember, false);
        }
    }

    private TokenRes generateTokensForMember(Member member, boolean isUser) {
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken();

        long count = refreshTokenRepository.countByMember(member);
        if (count > 4) {
            RefreshToken oldestToken = refreshTokenRepository.findFirstByMemberOrderByCreatedAtAsc(member);
            refreshTokenRepository.delete(oldestToken);
        }

        RefreshToken refreshTokenObject = new RefreshToken(refreshToken, member, LocalDateTime.now());
        refreshTokenRepository.save(refreshTokenObject);

        return new TokenRes(accessToken, refreshToken, isUser);
    }
}
