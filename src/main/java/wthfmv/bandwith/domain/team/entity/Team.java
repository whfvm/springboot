package wthfmv.bandwith.domain.team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
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
}
