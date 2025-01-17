package wthfmv.bandwith.domain.member.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import wthfmv.bandwith.domain.member.dto.res.TokenRes;
import wthfmv.bandwith.domain.member.service.MemberService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final RestTemplate restTemplate;
    private final MemberService memberService;
    @Value("${google.token.url}")
    private String googleAccessTokenUrl;

    @Value("${google.resource.url}")
    private String googleResourceUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @GetMapping("/oauth2/google")
    public ResponseEntity<?> googleLogin(
            @RequestParam("code") String code,
            @RequestParam("redirect") String redirect
    ){
        System.out.println(code);
        String accessToken = createAccessToken(code, redirect);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        JsonNode responseNode = restTemplate.exchange(googleResourceUrl, HttpMethod.GET, entity, JsonNode.class).getBody();
        System.out.println(responseNode);
        String id = responseNode.get("id").asText();

        TokenRes tokenRes = memberService.googleOAuth(id);
        return ResponseEntity.ok(tokenRes);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    private String createAccessToken(String code, String redirect){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirect);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(googleAccessTokenUrl, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();

        return accessTokenNode.get("access_token").asText();
    }
}
