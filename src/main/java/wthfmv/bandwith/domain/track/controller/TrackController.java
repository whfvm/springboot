package wthfmv.bandwith.domain.track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wthfmv.bandwith.domain.track.service.TrackService;

@RestController
@RequestMapping("/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
}
