package wthfmv.bandwith.domain.team.dto.req;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Create {
    private String name;
    private String profileImage;
    private int limitMember;
}
