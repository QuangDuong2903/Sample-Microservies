package com.quangduong.orderservice.filter;

import com.quangduong.commons.security.UserDetailsImpl;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> id = Optional.ofNullable(request.getHeader("X-Auth-User-Id"));
        Optional<String> authorities = Optional.ofNullable(request.getHeader("X-Auth-User-Authorities"));
        if (id.isPresent() && authorities.isPresent()) {
            UserDetails userDetails = new UserDetailsImpl(
                    Long.parseLong(id.get()),
                    id.get(),
                    "PASSWORD",
                    Arrays.stream(authorities.get()
                            .substring(1, authorities.get().length() - 1)
                            .split(",")).map(SimpleGrantedAuthority::new).toList()
            );
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
