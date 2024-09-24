package wthfmv.bandwith.domain.team.dto.res;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wthfmv.bandwith.domain.teamMember.entity.Position;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;

@Data
@Setter(AccessLevel.PROTECTED)
public class TeamListRes {
    private String bandId;
    private String image;
    private String bandName;
    private Position position;
    private String part;

    public TeamListRes(TeamMember teamMember){
        this.bandId = teamMember.getTeam().getId().toString();
        this.bandName = teamMember.getTeam().getName();
        this.position = teamMember.getPosition();
        this.part = teamMember.getPart();
    }
}
