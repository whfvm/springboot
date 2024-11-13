package wthfmv.bandwith.domain.teamMember.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.team.entity.Team;

import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_member_id")
    private UUID id;

    @Column(name = "team_member_position")
    private Position position;

    @Column(name = "team_member_part")
    private String part;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamMember(Position position, Member member, Team team, String part){
        this.position = position;
        this.member = member;
        this.team = team;
        this.part = part;
    }

    public void updatePart(String part) {
        this.part = part;
    }
}
