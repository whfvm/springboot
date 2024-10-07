package wthfmv.bandwith.domain.team.dto.res;

import lombok.*;
import wthfmv.bandwith.domain.team.entity.Policy;
import wthfmv.bandwith.domain.team.entity.Team;
import wthfmv.bandwith.domain.teamMember.entity.Position;
import wthfmv.bandwith.domain.teamMember.entity.TeamMember;
import wthfmv.bandwith.domain.track.entity.Track;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class TeamRes {
    private String uuid;
    private String name;
    private Integer limitMember;
    private String profileImage;
    private LocalDate createdAt;
    private List<MemberRes> memberResList;
    private List<TrackRes> trackResList;

    public TeamRes(Team team, List<Track> trackList){
        this.uuid = team.getId().toString();
        this.name = team.getName();
        this.limitMember = team.getLimitMember();
        this.profileImage = team.getProfileImage();
        this.createdAt = team.getCreatedAt();
        this.memberResList = team.getTeamMembers().stream()
                .map(MemberRes::new)
                .collect(Collectors.toList());
        this.trackResList = trackList.stream()
                .map(TrackRes::new)
                .collect(Collectors.toList());
    }
}

@Data
class MemberRes{
    private String profileImage;
    private String name;
    private Position position;
    private String part;
    // 프로필, 이름, 역할, 세션

    public MemberRes(TeamMember teamMember){
        this.profileImage = teamMember.getMember().getProfileImage();
        this.name = teamMember.getMember().getName();
        this.position = teamMember.getPosition();
        this.part = teamMember.getPart();
    }
}

@Data
class TrackRes{
    private String title;
    private LocalDate createdAt;
    private String artist;
    private String id;
    // 곡이름, 가수 만든 날짜

    public TrackRes(Track track){
        this.title = (String) track.getMetaData().get("title");
        this.createdAt = track.getCreatedAt();
        this.artist = (String) track.getMetaData().get("artist");
        this.id = track.getId();
    }
}
