package wthfmv.bandwith.domain.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocalLoginReq {
    String email;
    String password;
}
