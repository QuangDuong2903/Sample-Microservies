package com.quangduong.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayFilter implements GlobalFilter {

    private final JwtDecoder jwtDecoder;

    @Autowired
    public GatewayFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders()
                .getFirst("Authorization");
        if (token == null || token.startsWith("Basic"))
            return chain.filter(exchange);
        Jwt jwt = jwtDecoder.decode(token.substring(7));
        return chain.filter(
                exchange.mutate().request(
                                exchange.getRequest().mutate()
                                        .header("X-Auth-User-Id", jwt.getClaimAsString("id"))
                                        .header("X-Auth-User-Authorities",
                                                String.valueOf(jwt.getClaimAsString("authorities")))
                                        .build()
                        )
                        .build()
        );
    }
}
