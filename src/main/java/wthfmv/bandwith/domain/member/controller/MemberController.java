package wthfmv.bandwith.domain.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import wthfmv.bandwith.domain.member.dto.req.GoogleLoginReq;
import wthfmv.bandwith.domain.member.dto.req.MemberUpdateReq;
import wthfmv.bandwith.domain.member.dto.res.MemberRes;
import wthfmv.bandwith.domain.member.service.MemberService;
import wthfmv.bandwith.global.security.userDetails.CustomUserDetails;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 멤버 정보 조회
    @GetMapping()
    public ResponseEntity<MemberRes> member(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        MemberRes memberRes = memberService.member(userDetails.getUuid());
        return ResponseEntity.ok().body(memberRes);
    }

    @PutMapping()
    public ResponseEntity<String> update(
            @RequestBody MemberUpdateReq memberUpdateReq
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        memberService.update(userDetails.getUuid(), memberUpdateReq);

        return ResponseEntity.ok().body(userDetails.getUuid().toString() + "업데이트 성공");
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<String> delete(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        memberService.delete(userDetails.getUuid());

        return ResponseEntity.ok().body("탈퇴 완료");
    }
}
