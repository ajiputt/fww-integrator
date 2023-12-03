package com.telkomsel.fww.integrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkomsel.fww.integrator.payload.request.RequestLogin;
import com.telkomsel.fww.integrator.payload.request.RequestRegister;
import com.telkomsel.fww.integrator.payload.response.ResponseLogin;
import com.telkomsel.fww.integrator.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(value = AuthorizationController.class, excludeAutoConfiguration =
        {SecurityAutoConfiguration.class})
class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    SecurityContext securityContext;

    @MockBean
    private AuthorizationService authorizationService;

    @BeforeEach
    void init() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void login() throws Exception {

        RequestLogin requestLogin = RequestLogin.builder().username("temp1")
                .password("12345").build();

        when(authorizationService.processLogin(any())).thenReturn(ResponseLogin.builder()
                .token("token123")
                .expiredInMs(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestLogin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token").value(
                        "token123"));
    }

    @Test
    void register() throws Exception {
        RequestRegister requestRegister = RequestRegister.builder().username(
                        "integrationTest1")
                .password("12345").email("test@user.com").phone("09999999").firstName("first")
                .lastName("last").build();

        doNothing().when(authorizationService).processRegister(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRegister)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
}