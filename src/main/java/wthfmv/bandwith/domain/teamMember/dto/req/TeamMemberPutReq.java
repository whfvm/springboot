package wthfmv.bandwith.domain.teamMember.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamMemberPutReq {
    private String bandId;
    private String part;
}
