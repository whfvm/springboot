package wthfmv.bandwith.global.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
        @Server(url = "http://localhost:8080", description = "로컬 도메인")})
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi() {

        // accessToken 으로 인증 가능
        SecurityScheme accessTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("AccessToken")
                .description("AccessToken");

        // refreshToken 으로 입력 가능
        SecurityScheme refreshTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("RefreshToken")
                .description("RefreshToken");

        return new OpenAPI().components(
                        new Components()
                                .addSecuritySchemes("AccessToken",accessTokenScheme)
                                .addSecuritySchemes("RefreshToken", refreshTokenScheme))
                .info(getInfo());

    }

    private Info getInfo() {
        return new Info()
                .version("1.0.0")
                .description("BANDWITH")
                .title("API SERVER");
    }
}
