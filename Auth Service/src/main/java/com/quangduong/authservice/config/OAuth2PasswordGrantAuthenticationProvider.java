package com.quangduong.authservice.config;

import com.quangduong.authservice.utils.OAuth2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OAuth2PasswordGrantAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OAuth2AuthorizationService authorizationService;
    @Autowired
    private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // receive token from converter
        OAuth2PasswordGrantAuthenticationToken passwordGrantAuthenticationToken = (OAuth2PasswordGrantAuthenticationToken) authentication;

        // Ensure the client is authenticated
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2Utils.getAuthenticatedClientElseThrowInvalidClient(passwordGrantAuthenticationToken);

        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        // Ensure the client is configured to use this authorization grant type
        if (registeredClient == null ||
                !registeredClient.getAuthorizationGrantTypes().contains(passwordGrantAuthenticationToken.getGrantType()))
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);

        Set<String> authorizedScopes = Collections.emptySet();
        if (!CollectionUtils.isEmpty(passwordGrantAuthenticationToken.getScopes())) {
            passwordGrantAuthenticationToken.getScopes().forEach(scope -> {
                if (!registeredClient.getScopes().contains(scope))
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
            });
            authorizedScopes = new HashSet<>(passwordGrantAuthenticationToken.getScopes());
        }

        // Check user credentials
        String username = passwordGrantAuthenticationToken.getUsername();
        String password = passwordGrantAuthenticationToken.getPassword();

        Authentication credentialsAuthentication;
//                authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        try {
            credentialsAuthentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        // get authentication object
        OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken =
                (OAuth2ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        oAuth2ClientAuthenticationToken.setDetails(credentialsAuthentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(oAuth2ClientAuthenticationToken);

        // Generate access token
        DefaultOAuth2TokenContext tokenContext = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationGrantType(passwordGrantAuthenticationToken.getGrantType())
                .authorizationGrant(passwordGrantAuthenticationToken)
                .build();

        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);

        if (generatedAccessToken == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.",
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI));
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(),
                tokenContext.getAuthorizedScopes());

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .attribute(Principal.class.getName(), clientPrincipal)
                .principalName(clientPrincipal.getName())
                .authorizationGrantType(passwordGrantAuthenticationToken.getGrantType())
                .authorizedScopes(authorizedScopes);
        if (generatedAccessToken instanceof ClaimAccessor)
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(
                            OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                            ((ClaimAccessor) generatedAccessToken).getClaims())
            );
        else
            authorizationBuilder.accessToken(accessToken);

        OAuth2Authorization authorization = authorizationBuilder.build();
        authorizationService.save(authorization);

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
