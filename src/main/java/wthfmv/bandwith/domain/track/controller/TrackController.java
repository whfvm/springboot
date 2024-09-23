package wthfmv.bandwith.domain.track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wthfmv.bandwith.domain.track.dto.req.TrackPostReq;
import wthfmv.bandwith.domain.track.service.TrackService;

@RestController
@RequestMapping("/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping()
    public ResponseEntity<?> postTrack(@RequestBody TrackPostReq trackPostReq){
        trackService.postTrack(trackPostReq);
        return null;
    }
}
