package wthfmv.bandwith.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wthfmv.bandwith.domain.member.dto.req.MemberUpdateReq;
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

    @Column(name = "member_provider")
    private String provider;

    @Column(name = "member_provider_id")
    private String providerId;

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
    public Member(LocalDate birth, String name, String introduce, String profileImage) {
        this.birth = birth;
        this.name = name;
        this.introduce = introduce;
        this.profileImage = profileImage;
    }

    public Member(String provider, String providerId){
        this.birth = LocalDate.now();
        this.introduce = "자기소개를 작성해 주세요";
        this.profileImage = null;
        this.name = "BANDWITH";
        this.provider = provider;
        this.providerId = providerId;
    }

    public void update(MemberUpdateReq memberUpdateReq) {
        this.birth = memberUpdateReq.getBirth() == null ? this.birth : memberUpdateReq.getBirth();
        this.name = memberUpdateReq.getName() == null ? this.name : memberUpdateReq.getName();
        this.introduce = memberUpdateReq.getIntroduce() == null ? this.introduce : memberUpdateReq.getIntroduce();
    }
}
