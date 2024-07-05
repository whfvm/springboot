package wthfmv.bandwith.domain.teamMember.entity;

import jakarta.persistence.*;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.team.entity.Team;

import java.util.UUID;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_member_id")
    private UUID id;

    @Column(name = "team_member_position")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
