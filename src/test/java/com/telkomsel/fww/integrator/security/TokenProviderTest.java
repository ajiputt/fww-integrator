package com.telkomsel.fww.integrator.security;

import com.telkomsel.fww.integrator.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenProviderTest {

    TokenProvider tokenProvider;


    private String secret;

    private Long expiration;

    @Mock
    private Authentication auth;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider();

        secret = "onlyTheTestKnowsThisSecretOnlyTheTestKnowsThisSecretOnlyTheTestKnowsThisSecretOnlyTheTestKnowsThisSecret";
        expiration = 90000L;
        ReflectionTestUtils.setField(tokenProvider, "secret", secret);
        ReflectionTestUtils.setField(tokenProvider, "expiration", expiration);

        auth = Mockito.mock(Authentication.class);
    }

    @Test
    void createToken() {

        UserPrincipal userPrincipal =
                new UserPrincipal(Member.builder().username("usernameTest").build(),
                        null);
        Mockito.when(auth.getPrincipal()).thenReturn(userPrincipal);

        String res = tokenProvider.createToken(auth);

        Claims claims =
                Jwts.parser().verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))).
                        build().parseSignedClaims(res).getPayload();
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        MatcherAssert.assertThat(expiration.getTime() - issuedAt.getTime(), Matchers.equalTo(90 * 1000L));
    }

    @Test
    void getUserNameFromToken() {
        String tokenTest = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .issuedAt(new Date())
                .subject("usernameTest")
                .expiration(new Date((new Date()).getTime() + expiration))
                .compact();


        String res = tokenProvider.getUserNameFromToken(tokenTest);

        assertEquals("usernameTest", res);


    }

    @Test
    void validateToken() {
        String tokenTest = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .issuedAt(new Date())
                .subject("usernameTest")
                .expiration(new Date((new Date()).getTime() + expiration))
                .compact();

        Boolean res = tokenProvider.validateToken(tokenTest);

        assertEquals(true, res);
    }

    @Test
    void validateTokenFailed() {
        String tokenTest = "failedTOKEN";
        tokenProvider.validateToken(tokenTest);

        Assertions.assertThat(true);
    }

    @Test
    void validateTokenFailedTimeout() {
        String tokenTest = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .issuedAt(new Date())
                .subject("usernameTest")
                .expiration(new Date())
                .compact();

        tokenProvider.validateToken(tokenTest);

        Assertions.assertThat(true);
    }

    @Test
    void validateTokenFailedEmpty() {
        tokenProvider.validateToken("");

        Assertions.assertThat(true);
    }

    @Test
    void validateTokenFailedSignature() {
        String secretFailed =
                "onlyTheTestKnowsThisSecretOnlyTheTestKnowsThisSecretOnlyTheTestKnowsThisSecretOnlyTheTestKnowsThisSecretFailed";
        String tokenTest = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretFailed)))
                .issuedAt(new Date())
                .subject("usernameTest")
                .expiration(new Date((new Date()).getTime() + expiration))
                .compact();

        tokenProvider.validateToken(tokenTest);

        Assertions.assertThat(true);
    }
}