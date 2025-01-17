package wthfmv.bandwith.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wthfmv.bandwith.global.security.filter.JwtAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(SessionManagementConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login/**").permitAll()
                        // 웹소켓 연결
                        .requestMatchers("/ws/**").permitAll()
                        //멤버 관련 API
                        .requestMatchers(HttpMethod.GET, "/member").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/member").authenticated()
                        // 트랙 관련 API
                        .requestMatchers(HttpMethod.POST, "/track").authenticated()
                        .requestMatchers(HttpMethod.GET, "/track").authenticated()
                        //팀 관련 API
                        .requestMatchers(HttpMethod.POST, "/team").authenticated()
                        .requestMatchers(HttpMethod.GET, "/team/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/team").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/team").authenticated()
                        // 팀-멤버 관련 API
                        .requestMatchers(HttpMethod.PUT, "/teamMember").authenticated()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/v2/api-docs",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }
}
