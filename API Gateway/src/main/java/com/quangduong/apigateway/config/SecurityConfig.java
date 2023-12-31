package com.quangduong.apigateway.config;

import com.quangduong.apigateway.exception.AccessDeniedExceptionHandler;
import com.quangduong.apigateway.exception.AuthenticationExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
    }

    @Bean
    public AuthenticationExceptionHandler authenticationExceptionHandler() {
        return new AuthenticationExceptionHandler();
    }

    @Bean
    public AccessDeniedExceptionHandler accessDeniedExceptionHandler() {
        return new AccessDeniedExceptionHandler();
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST, "api/oauth2/token").permitAll()
                        .pathMatchers(HttpMethod.POST, "api/signup").permitAll()
                        .pathMatchers("eureka/dashboard").permitAll()
                        .pathMatchers(HttpMethod.GET, "api/users").hasAuthority("SCOPE_ROLE_ADMIN")
                        .anyExchange().authenticated()
                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(authenticationExceptionHandler())
//                        .accessDeniedHandler(accessDeniedExceptionHandler())
//                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(authenticationExceptionHandler())
                        .accessDeniedHandler(accessDeniedExceptionHandler())
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }

}
