package com.telkomsel.fww.integrator.security;

import com.telkomsel.fww.integrator.domain.Member;
import com.telkomsel.fww.integrator.feign.service.MemberClientService;
import com.telkomsel.fww.integrator.feign.service.MemberTokenClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    CustomUserDetailsService customUserDetailsService;

    @Mock
    private MemberClientService memberClientService;

    @Mock
    private MemberTokenClientService memberTokenClientService;

    private Member users;

    @BeforeEach
    void init() {
        customUserDetailsService =
                new CustomUserDetailsService(memberClientService, memberTokenClientService);
        users = Member.builder().username("usernameTest").
                password("11111").email("abc@email.test").build();
    }

    @Test
    void loadUserByUsername() {
        when(memberClientService.getMemberByUsername(any(String.class))).thenReturn(users);

        UserDetails res = customUserDetailsService.loadUserByUsername("usernameTest");
        assertEquals("usernameTest", res.getUsername());
    }

    @Test
    void loadUserByUsername_failed() {
        doThrow(UsernameNotFoundException.class).when(memberClientService)
                .getMemberByUsername(anyString());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("usernameTest"));
    }

    @Test
    void isTokenValid() {
        when(memberTokenClientService.existsByUsernameAndToken(anyString(),
                anyString())).thenReturn(true);

        Boolean resp = customUserDetailsService.isTokenValid("username",
                "token");

        assertEquals(resp, true);
    }
}