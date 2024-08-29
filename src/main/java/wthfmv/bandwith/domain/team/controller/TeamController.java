package wthfmv.bandwith.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wthfmv.bandwith.domain.team.dto.req.Create;
import wthfmv.bandwith.domain.team.service.TeamService;

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
        String publish = teamService.publish(bandID);
        return ResponseEntity.ok().body(publish);
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
        // 1. 토큰에서 멤버 uuid 가져옴

        teamService.sign(code);

       return ResponseEntity.ok().body(
               "a"
       );
    }
}
