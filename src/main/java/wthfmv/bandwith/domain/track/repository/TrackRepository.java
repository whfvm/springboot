package wthfmv.bandwith.domain.track.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.track.entity.Track;

@Repository
public interface TrackRepository extends MongoRepository<Track, String> {
}
