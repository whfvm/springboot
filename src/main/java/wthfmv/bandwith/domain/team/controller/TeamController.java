package wthfmv.bandwith.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wthfmv.bandwith.domain.team.dto.req.Create;
import wthfmv.bandwith.domain.team.dto.res.TeamListRes;
import wthfmv.bandwith.domain.team.dto.res.TeamRes;
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
     * @param create
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> create(Create create){

        teamService.create(create);

        return ResponseEntity.ok().body(
            "팀 저장 완료"
        );
    }

    /**
     * 팀 1개 정보 리턴
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> team(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String teamId
    ){

        String userUUID = customUserDetails.getUuid().toString();
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
     * @param field
     * @param search
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<String> delete(
            @RequestParam(defaultValue = "id") String field,
            @RequestParam String search
    ){
        teamService.delete(search);

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
    public ResponseEntity<String> sign(
            @RequestParam String code
    ){
        teamService.sign(code);

       return ResponseEntity.ok().body(
               "가입완료"
       );
    }
}
