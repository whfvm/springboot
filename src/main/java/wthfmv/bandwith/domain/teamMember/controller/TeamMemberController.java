package wthfmv.bandwith.domain.teamMember.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wthfmv.bandwith.domain.team.dto.req.TeamCreateReq;
import wthfmv.bandwith.domain.team.dto.res.TeamCreateRes;
import wthfmv.bandwith.domain.teamMember.dto.req.TeamMemberPutReq;
import wthfmv.bandwith.domain.teamMember.service.TeamMemberService;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/teamMember")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @PutMapping()
    public ResponseEntity<?> put(
            @RequestBody TeamMemberPutReq teamMemberPutReq,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        teamMemberService.put(teamMemberPutReq, customUserDetails.getUuid());

        return ResponseEntity.ok().body(
                "정보 수정 완료"
        );
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(
            @RequestParam String bandId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        teamMemberService.delete(bandId, customUserDetails.getUuid());

        return ResponseEntity.ok().body(
                bandId + "탈퇴 완료"
        );
    }
}