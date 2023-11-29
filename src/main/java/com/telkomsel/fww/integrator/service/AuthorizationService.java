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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthorizationService {

    @Value("${app.jwt.expiration}")
    private Long expiration;


    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final MemberClientService memberClientService;

    private final MemberTokenClientService memberTokenClientService;


    public AuthorizationService(AuthenticationManager authenticationManager,
                                PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
                                MemberClientService memberClientService,
                                MemberTokenClientService memberTokenClientService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.memberClientService = memberClientService;
        this.memberTokenClientService = memberTokenClientService;
    }

    public ResponseLogin processLogin(RequestLogin requestLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestLogin.getUsername(),
                        requestLogin.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);


        memberTokenClientService.postMemberToken(MemberToken.builder()
                .username(requestLogin.getUsername())
                .token(token)
                .build());

        return ResponseLogin.builder().token(token).expiredInMs(expiration).build();

    }

    public void processRegister(RequestRegister requestRegister) {
        if (memberClientService.existMemberByUsernameOrEmail(requestRegister.getUsername(),
                requestRegister.getEmail())) {
            throw new RegisterException();
        }

        Member member = Member.builder().username(requestRegister.getUsername())
                .password(passwordEncoder.encode(requestRegister.getPassword()))
                .firstName(requestRegister.getFirstName())
                .lastName(requestRegister.getLastName())
                .email(requestRegister.getEmail())
                .phone(requestRegister.getPhone())
                .address(requestRegister.getAddress())
                .createdAt(LocalDateTime.now()).build();
        memberClientService.postReservation(member);
    }
}
