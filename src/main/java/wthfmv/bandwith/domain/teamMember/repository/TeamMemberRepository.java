package wthfmv.bandwith.domain.teamMember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.member.entity.Member;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {
    Optional<TeamMember> findByMemberAndTeam(Member member, Team team);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.member.id =:userUUID")
    List<TeamMember> findByMember(@Param("userUUID") UUID userUUID);
}
