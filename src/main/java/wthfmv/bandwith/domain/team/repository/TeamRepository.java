package wthfmv.bandwith.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.team.entity.Team;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
}
