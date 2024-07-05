package wthfmv.bandwith.domain.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginReq {
    // 구글 로그인용 토큰
    private String token;
}
