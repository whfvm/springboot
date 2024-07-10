package wthfmv.bandwith.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "member_token")
    private String token;

    @Column(name = "member_login_type")
    private LoginType loginType;

    @Column(name = "member_birth")
    private LocalDate birth;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_introduce")
    private String introduce;

    @Column(name = "member_profile_image")
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers;
    public Member(String token, LocalDate birth, String name, String introduce, String profileImage) {
        this.token = token;
        this.birth = birth;
        this.name = name;
        this.introduce = introduce;
        this.profileImage = profileImage;
    }
}
