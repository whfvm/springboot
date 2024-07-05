package wthfmv.bandwith.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wthfmv.bandwith.domain.member.dto.req.GoogleLoginReq;
import wthfmv.bandwith.domain.member.service.MemberService;
import wthfmv.bandwith.domain.member.service.oauth.GoogleOAuthService;
import wthfmv.bandwith.domain.member.service.oauth.OAuthService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/login/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginReq googleLoginReq){

        googleOAuthService.login(googleLoginReq.getToken());

        return ResponseEntity
                .ok()
                .body("ok");
    }
}
