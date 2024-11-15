package wthfmv.bandwith.domain.track.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.track.entity.Track;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackRepository extends MongoRepository<Track, String> {
    @Query("{ 'band_id': ?0 }")
    List<Track> findByBandId(UUID bandId);
}
