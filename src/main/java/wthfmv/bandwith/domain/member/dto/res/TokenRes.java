package wthfmv.bandwith.domain.member.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRes {
    private String accessToken;
    private String refreshToken;
    private Boolean isUser;
}
