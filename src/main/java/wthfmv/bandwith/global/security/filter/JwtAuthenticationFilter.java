package wthfmv.bandwith.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wthfmv.bandwith.domain.refreshToken.entity.RefreshToken;
import wthfmv.bandwith.domain.refreshToken.repository.RefreshTokenRepository;
import wthfmv.bandwith.global.security.jwt.JwtProvider;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetailService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        String accessToken = request.getHeader("accessToken");
        String refreshToken = request.getHeader("refreshToken");

        if(accessToken == null && refreshToken == null){
//            throw new RuntimeException("인증 필요");
            filterChain.doFilter(request,response);
        }

        if(accessToken != null){
            // id 추출
            String id = jwtProvider.getID(accessToken);
            // 유저 인증 객체 생성
            UserDetails userDetails = customUserDetailService.loadUserByUUID(UUID.fromString(id));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 필터 계속
            filterChain.doFilter(request, response);
        }

        if(refreshToken != null){
            RefreshToken refreshTokenObject = refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                    () -> new RuntimeException("재로그인 필요")
            );
            // 새 accessToken 생성
            String newAccessToken = jwtProvider.createAccessToken(refreshTokenObject.getMember().getId());
            // 새 refreshToken 생성
            String newRefreshToken = jwtProvider.createRefreshToken();
            // refreshToken value 업데이트하고 저장
            refreshTokenObject.updateRefreshToken(newRefreshToken);
            refreshTokenRepository.save(refreshTokenObject);
            // 유저 인증 객체 생성
            UserDetails userDetails = customUserDetailService.loadUserByUUID(UUID.fromString(refreshTokenObject.getMember().getId().toString()));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 헤더에 토큰 삽입
            response.setHeader("accessToken", newAccessToken);
            response.setHeader("refreshToken", newRefreshToken);
            // 필터 계속
            filterChain.doFilter(request, response);
        }
    }
}
