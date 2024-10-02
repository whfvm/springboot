package wthfmv.bandwith.domain.track.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import wthfmv.bandwith.domain.track.entity.Track;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track, String> {
    @Query("{ 'metaData.band_Id': ?0 }")
    List<Track> findByBandId(String bandId);
}
