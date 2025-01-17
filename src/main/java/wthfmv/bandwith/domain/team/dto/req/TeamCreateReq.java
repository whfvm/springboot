package wthfmv.bandwith.domain.team.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeamCreateReq {
    private String name;
    private String profileImage;
    private Integer limitMember;
}
