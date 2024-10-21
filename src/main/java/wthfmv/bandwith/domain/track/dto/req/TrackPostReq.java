package wthfmv.bandwith.domain.track.dto.req;

import lombok.Data;

import java.util.Map;

@Data
public class TrackPostReq {
    private Map<String, Object> metaData;
    private Map<String, Object> track;
}
