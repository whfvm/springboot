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
    private UUID band_id;

    private Map<String, Object> metadata;

    private Map<String, Object> tracks;

    private LocalDate createdAt;

    public Track(TrackPostReq trackPostReq){
        this.band_id = UUID.fromString(trackPostReq.getBand_id());
        this.metadata = trackPostReq.getMetadata();
        this.tracks = trackPostReq.getTracks();
        this.createdAt = LocalDate.now();
    }
}
