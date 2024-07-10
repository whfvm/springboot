package wthfmv.bandwith.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wthfmv.bandwith.domain.team.dto.req.Create;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.team.repository.TeamRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void create(Create create) {
        Team team = new Team(
                create.getName(),
                create.getLimitMember(),
                create.getProfileImage()
        );

        teamRepository.save(team);
    }

    @Transactional
    public void delete(String search) {
        teamRepository.deleteById(UUID.fromString(search));
    }
}
