package wthfmv.bandwith.domain.track.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import wthfmv.bandwith.domain.track.entity.Track;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TrackRes {
    private Track track;
    private String uuid;
}
