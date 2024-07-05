package wthfmv.bandwith.domain.member.entity;

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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "member_token")
    private String token;

    @Column(name = "member_LoginType")
    private LoginType loginType;

    @Column(name = "member_birth")
    private LocalDate birth;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_introduce")
    private String introduce;

    @Column(name = "member_profileImage")
    private String profileImage;

    public Member(String token, LocalDate birth, String name, String introduce, String profileImage) {
        this.token = token;
        this.birth = birth;
        this.name = name;
        this.introduce = introduce;
        this.profileImage = profileImage;
    }
}
