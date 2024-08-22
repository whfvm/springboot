package wthfmv.bandwith.domain.refreshToken.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import wthfmv.bandwith.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken {
    @Id
    @Column(name = "refreshToken_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "refreshToken_value")
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name="refreshToken_created_at")
    private LocalDateTime createdAt;

    public void updateRefreshToken(String newRefreshToken) {
        this.token = newRefreshToken;
    }

    public RefreshToken(String token, Member member, LocalDateTime createdAt){
        this.token = token;
        this.member = member;
        this.createdAt = createdAt;
    }
}
