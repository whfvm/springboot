package wthfmv.bandwith.domain.track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wthfmv.bandwith.domain.track.dto.req.TrackPostReq;
import wthfmv.bandwith.domain.track.dto.res.TrackRes;
import wthfmv.bandwith.domain.track.entity.Track;
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

    @GetMapping()
    public ResponseEntity<TrackRes> getTrack(
            @RequestParam String trackId
    ){
        TrackRes trackRes = trackService.getTrack(trackId);

        return ResponseEntity.ok().body(
                trackRes
        );
    }
}
