package wthfmv.bandwith.domain.team.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter(AccessLevel.PROTECTED)
public class TeamCreateRes {
    private String teamId;
    private String teamName;
}
