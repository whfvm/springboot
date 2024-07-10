package wthfmv.bandwith.global.security.jwt;

import com.google.api.client.util.Data;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    Key key;

    @PostConstruct
    public void init() { key = Keys.secretKeyFor(SignatureAlgorithm.HS256);}

    /**
     * 팀 참여 코드 생성 함수
     * @param uuid 참여 코드를 생성할 팀 uuid
     * @return 참여코드 String
     */
    public String createParticipationCode(UUID uuid){
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours((6)).toMillis()))
                .setSubject(uuid.toString())
                .signWith(key)
                .compact();
    }

    public String getID(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }
}
