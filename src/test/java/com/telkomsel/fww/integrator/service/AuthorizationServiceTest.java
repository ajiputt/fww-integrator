package com.telkomsel.fww.integrator.service;

import com.telkomsel.fww.integrator.domain.Member;
import com.telkomsel.fww.integrator.domain.MemberToken;
import com.telkomsel.fww.integrator.exception.RegisterException;
import com.telkomsel.fww.integrator.feign.service.MemberClientService;
import com.telkomsel.fww.integrator.feign.service.MemberTokenClientService;
import com.telkomsel.fww.integrator.payload.request.RequestLogin;
import com.telkomsel.fww.integrator.payload.request.RequestRegister;
import com.telkomsel.fww.integrator.payload.response.ResponseLogin;
import com.telkomsel.fww.integrator.security.TokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    AuthorizationService authorizationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private MemberClientService memberClientService;

    @Mock
    private MemberTokenClientService memberTokenClientService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication auth;

    @BeforeEach
    void init() {
        authorizationService = new AuthorizationService(authenticationManager,
                passwordEncoder, tokenProvider, memberClientService, memberTokenClientService);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void processLogin() {
        RequestLogin requestLogin =
                RequestLogin.builder().username("usernameTest").password("abc").build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

        String token = "testToken123";
        when(tokenProvider.createToken(auth)).thenReturn(token);

        when(memberTokenClientService.postMemberToken(any(MemberToken.class))).thenReturn(MemberToken.builder()
                .token(token).build());

        ResponseLogin res =
                authorizationService.processLogin(requestLogin);

        Assertions.assertThat(res.getToken()).isEqualTo("testToken123");
    }

    @Test
    void processRegister() {
        RequestRegister requestRegister = RequestRegister.builder()
                .username("testRegister")
                .password("testPassword")
                .email("abc@def.com")
                .firstName("test first")
                .lastName("test last")
                .phone("08123213").build();
        when(memberClientService.existMemberByUsernameOrEmail(any(String.class), any(String.class))).thenReturn(false);

        when(memberClientService.postReservation(any(Member.class))).thenReturn(Member.builder()
                .username("testRegister").build());

        authorizationService.processRegister(requestRegister);
        Assertions.assertThat(true);

    }

    @Test
    void processRegisterFailed() {
        RequestRegister requestRegister = RequestRegister.builder()
                .username("testRegister")
                .password("testPassword")
                .email("abc@def.com")
                .firstName("test first")
                .lastName("test last")
                .phone("08123213").build();
        when(memberClientService.existMemberByUsernameOrEmail(any(String.class), any(String.class))).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(RegisterException.class, () -> {
            authorizationService.processRegister(requestRegister);
        });


    }

}