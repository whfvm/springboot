package wthfmv.bandwith.domain.refreshToken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.refreshToken.entity.RefreshToken;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String refreshToken);
    long countByMember(Member member);

    RefreshToken findFirstByMemberOrderByCreatedAtAsc(Member member);
}
