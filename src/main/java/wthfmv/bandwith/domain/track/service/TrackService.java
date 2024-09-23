package wthfmv.bandwith.domain.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wthfmv.bandwith.domain.track.dto.req.TrackPostReq;
import wthfmv.bandwith.domain.track.entity.Track;
import wthfmv.bandwith.domain.track.repository.TrackRepository;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    public void postTrack(TrackPostReq trackPostReq) {
        trackRepository.save(new Track(trackPostReq));
    }
}
