package wthfmv.bandwith.domain.team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wthfmv.bandwith.domain.team.service.TeamService;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "team_name")
    private String name;

    @Column(name = "team_limit_member")
    private Integer limitMember;

    @Column(name = "team_policy")
    private Policy policy;

    @Column(name = "team_profile_image")
    private String profileImage;

    @Column(name = "team_created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers;

    public Team(String name, int limitMember, String profileImage){
        this.name = name;
        this.limitMember = limitMember;
        this.profileImage = profileImage;
        this.createdAt = LocalDate.now();
        this.policy = Policy.FREE;
    }
}
