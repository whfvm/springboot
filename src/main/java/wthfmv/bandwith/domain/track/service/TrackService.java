package wthfmv.bandwith.domain.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.track.dto.req.TrackPostReq;
import wthfmv.bandwith.domain.track.entity.Track;
import wthfmv.bandwith.domain.track.repository.TrackRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    @Transactional
    public void postTrack(TrackPostReq trackPostReq) {
        trackRepository.save(new Track(trackPostReq));
    }

    @Transactional
    public Object getTrack(String trackId){
        return trackRepository.findById(trackId).orElseThrow(
                () -> new RuntimeException("해당 트랙 없음")
        );
    }
    @Transactional
    public Optional<Track> updateAndGetTrack(String id) {
        return trackRepository.findById(id);
    }
}
