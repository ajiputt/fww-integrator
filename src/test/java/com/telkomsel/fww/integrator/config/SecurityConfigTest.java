package com.telkomsel.fww.integrator.config;

import com.telkomsel.fww.integrator.security.CustomUserDetailsService;
import com.telkomsel.fww.integrator.security.RestAuthenticationEntryPoint;
import com.telkomsel.fww.integrator.security.TokenAuthenticationFilter;
import com.telkomsel.fww.integrator.security.TokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    SecurityConfig securityConfig;

    @Mock
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private AuthenticationConfiguration authConfig;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @BeforeEach
    void init() {
        securityConfig = new SecurityConfig(restAuthenticationEntryPoint,
                tokenProvider, customUserDetailsService);
        authConfig = Mockito.mock(AuthenticationConfiguration.class);
        httpSecurity = Mockito.mock(HttpSecurity.class);
        authenticationManagerBuilder =
                Mockito.mock(AuthenticationManagerBuilder.class);
    }

    @Test
    void authenticationJwtTokenFilter() {
        TokenAuthenticationFilter resp =
                securityConfig.authenticationJwtTokenFilter();
        Assertions.assertThat(resp).isNotNull();
    }

    @Test
    void authenticationManager() throws Exception {
        AuthenticationManager resp =
                securityConfig.authenticationManager(authConfig);
        Assertions.assertThat(resp).isNull();
    }

    @Test
    void passwordEncoder() {
        PasswordEncoder resp = securityConfig.passwordEncoder();
        Assertions.assertThat(resp).isNotNull();
    }

    @Test
    void authManager() throws Exception {

//        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder()).and().build();
//        Mockito.when(httpSecurity.getSharedObject(any())).thenReturn(authenticationManagerBuilder);
//        Mockito.when(authenticationManagerBuilder.userDetailsService(any())).thenReturn(AuthenticationManager);
//        AuthenticationManager resp = securityConfig.authManager(httpSecurity);
//        Assertions.assertThat(resp).isNotNull();
    }

    @Test
    void filterChain() throws Exception {
//        SecurityFilterChain resp = securityConfig.filterChain(httpSecurity);
//        Assertions.assertThat(resp).isNotNull();
    }
}