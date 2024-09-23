package wthfmv.bandwith.domain.team.dto.res;

import lombok.*;
import wthfmv.bandwith.domain.team.entity.Policy;
import wthfmv.bandwith.domain.team.entity.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class TeamRes {
    private String uuid;
    private String name;
    private Integer limitMember;
    private Policy policy;
    private String profileImage;
    private LocalDate createdAt;

    public TeamRes(Team team){
        this.uuid = team.getId().toString();
        this.name = team.getName();
        this.limitMember = team.getLimitMember();
        this.policy = team.getPolicy();
        this.profileImage = team.getProfileImage();
        this.createdAt = team.getCreatedAt();
    }
}
