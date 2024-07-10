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
    public ResponseEntity<String> publish(){
        // 1. 요청자 uuid 가져와야 함
        // 2. 서비스에 넘김
        // 3. 서비스에서 토큰 발행

        return ResponseEntity.ok().body(
                "a"
        );
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

       return ResponseEntity.ok().body(
               "a"
       );
    }
}
