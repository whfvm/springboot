package wthfmv.bandwith.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.member.entity.LoginType;
import wthfmv.bandwith.domain.member.entity.Member;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByTokenAndLoginType(String token, LoginType loginType);
}
