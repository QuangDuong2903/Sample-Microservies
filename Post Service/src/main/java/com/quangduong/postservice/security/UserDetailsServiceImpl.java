package com.quangduong.postservice.security;

import com.quangduong.exceptionhandler.security.UserDetailsImpl;
import com.quangduong.postservice.client.user.UserClient;
import com.quangduong.postservice.client.user.dto.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<UserDetailsResponse> response = userClient.getUserDetails(username);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            return new UserDetailsImpl(
                    response.getBody().getId(),
                    response.getBody().getUsername(),
                    response.getBody().getPassword(),
                    response.getBody().getAuthorities().stream().map(SimpleGrantedAuthority::new).toList()
            );
        return null;
    }
}
