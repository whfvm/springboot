package wthfmv.bandwith.domain.track.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import wthfmv.bandwith.domain.track.dto.req.TrackPostReq;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Document(collection = "track")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    @Id
    private String id;

    private Map<String, Object> metaData;

    private Map<String, Object> track;

    private LocalDate createdAt;

    public Track(TrackPostReq trackPostReq){
        this.metaData = trackPostReq.getMetaData();
        this.track = trackPostReq.getTrack();
        this.createdAt = LocalDate.now();
    }
}
