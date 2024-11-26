package wthfmv.bandwith.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.member.entity.LoginType;
import wthfmv.bandwith.domain.member.entity.Member;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.email =:email and m.provider =:provider")
    Optional<Member> findByEmailAndProvider(@Param("email") String email, @Param("provider") String provider);
}
