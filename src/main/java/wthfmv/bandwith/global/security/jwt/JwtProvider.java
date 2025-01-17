package wthfmv.bandwith.global.security.jwt;

import com.google.api.client.util.Data;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getID(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    /**
     * RefreshToken 생성기
     * @return
     */
    public String createRefreshToken(){
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours((1)).toMillis()))
                .signWith(key)
                .compact();
    }

    /**
     * AccessToken 생성기
     * @param uuid 유저 uuid를 받아 uuid 생성
     * @return
     */
    public String createAccessToken(UUID uuid){
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes((3)).toMillis()))
                .setSubject(uuid.toString())
                .signWith(key)
                .compact();
    }
}
