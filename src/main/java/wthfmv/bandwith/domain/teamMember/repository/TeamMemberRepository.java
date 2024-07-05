package wthfmv.bandwith.domain.teamMember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;

import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {
}
