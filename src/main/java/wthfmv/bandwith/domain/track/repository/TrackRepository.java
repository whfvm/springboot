package wthfmv.bandwith.domain.track.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import wthfmv.bandwith.domain.track.entity.Track;

public interface TrackRepository extends MongoRepository<Track, String> {
}
