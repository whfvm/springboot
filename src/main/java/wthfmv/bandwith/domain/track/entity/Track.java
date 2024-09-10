package wthfmv.bandwith.domain.track.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

@Document(collation = "track")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    @Id
    private String id;

    private String artist;

    private String title;

    private UUID teamID;

    private Map<String, Object> track;
}
