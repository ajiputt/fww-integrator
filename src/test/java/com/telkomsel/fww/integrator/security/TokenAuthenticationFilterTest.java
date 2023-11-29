package com.telkomsel.fww.integrator.security;

import com.telkomsel.fww.integrator.domain.Member;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenAuthenticationFilterTest {

    TokenAuthenticationFilter tokenAuthenticationFilter;

    @Mock
    private TokenProvider tokenProvider;


    @Mock
    private CustomUserDetailsService customUserDetailsService;

    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private MockFilterChain mockFilterChain;
    private UserDetails userDetails;

    @BeforeEach
    void init() {
        tokenAuthenticationFilter =
                new TokenAuthenticationFilter(tokenProvider,
                        customUserDetailsService);

        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockFilterChain = new MockFilterChain();

        mockRequest.addHeader("Authorization", "Bearer TestTokenBearer");

        Member member = Member.builder().username("TestUser")
                .email("abc@email.test")
                .password("testPass").build();
        userDetails = new UserPrincipal(member, null);

        SecurityContextHolder.clearContext();

    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
        MockFilterChain mockFilterChainSpy = spy(mockFilterChain);
        when(tokenProvider.validateToken(any(String.class))).thenReturn(true);
        when(tokenProvider.getUserNameFromToken(any(String.class))).thenReturn("TestUser");
        when(customUserDetailsService.isTokenValid(any(String.class),
                anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername((any(String.class)))).thenReturn(userDetails);

        tokenAuthenticationFilter.doFilter(mockRequest, mockResponse, mockFilterChainSpy);

        verify(mockFilterChainSpy, times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    void doFilterInternal_failed() throws ServletException, IOException {
        MockFilterChain mockFilterChainSpy = spy(mockFilterChain);
        when(tokenProvider.validateToken(any(String.class))).thenReturn(true);
        when(tokenProvider.getUserNameFromToken(any(String.class))).thenReturn("TestUser");
        when(customUserDetailsService.isTokenValid(any(String.class),
                anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername((any(String.class)))).thenReturn(userDetails);

        tokenAuthenticationFilter.doFilter(mockRequest, mockResponse, mockFilterChainSpy);

        verify(mockFilterChainSpy, times(1)).doFilter(mockRequest, mockResponse);
    }

}