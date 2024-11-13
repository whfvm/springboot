package wthfmv.bandwith.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wthfmv.bandwith.domain.team.dto.req.TeamCreateReq;
import wthfmv.bandwith.domain.team.dto.res.TeamCreateRes;
import wthfmv.bandwith.domain.team.dto.res.TeamListRes;
import wthfmv.bandwith.domain.team.dto.res.TeamRes;
import wthfmv.bandwith.domain.team.dto.res.TeamSignRes;
import wthfmv.bandwith.domain.team.service.TeamService;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/team")
@Validated
public class TeamController {

    private final TeamService teamService;
    
    // 멤버 탈퇴는 자유, 리더는 강제로 가능
    // 신규 멤버는 리더만 가입 허가 내려줄 수 있음

    /**
     *
     * @param teamCreateReq
     * @return
     */
    @PostMapping()
    public ResponseEntity<TeamCreateRes> create(@RequestBody TeamCreateReq teamCreateReq){

        TeamCreateRes teamCreateRes = teamService.create(teamCreateReq);

        return ResponseEntity.ok().body(
            teamCreateRes
        );
    }

    /**
     * 팀 1개 정보 리턴
     * @return
     */
    @GetMapping()
    public ResponseEntity<TeamRes> team(
            @RequestParam String teamId
    ){
        TeamRes teamRes = teamService.team(teamId);

        return ResponseEntity.ok().body(
                teamRes
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<TeamListRes>> teamList(@AuthenticationPrincipal CustomUserDetails customUserDetails){

        String userUUID = customUserDetails.getUuid().toString();

        List<TeamListRes> teamListRes = teamService.list(userUUID);

        return ResponseEntity.ok().body(teamListRes);
    }

    /**
     *
     * @param customUserDetails
     * @param teamId 지우려는 팀 아이디
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String teamId
    ){
        String userUUID = customUserDetails.getUuid().toString();
        teamService.delete(teamId, userUUID);

        return ResponseEntity.ok().body(
                "팀 삭제 완료"
        );
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publish(
            @RequestParam String bandID
    ){
        String randomCode = teamService.publish(bandID);
        return ResponseEntity.ok().body(randomCode);
    }

    /**
     *
     * @param code
     * @return
     */
    @GetMapping("/sign")
    public ResponseEntity<TeamSignRes> sign(
            @RequestParam String code
    ){
        TeamSignRes sign = teamService.sign(code);

        return ResponseEntity.ok().body(
               sign
       );
    }
}
