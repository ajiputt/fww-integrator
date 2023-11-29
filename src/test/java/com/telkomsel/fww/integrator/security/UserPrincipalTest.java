package com.telkomsel.fww.integrator.security;

import com.telkomsel.fww.integrator.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserPrincipalTest {

    UserPrincipal userDetails;

    @BeforeEach
    void init() {
        Member member = Member.builder().username("TestUser")
                .email("abc@email.test")
                .password("testPass").build();
        userDetails = new UserPrincipal(member, null);
    }

    @Test
    void create() {
        Member member = Member.builder().username("TestUser")
                .email("abc@email.test")
                .password("testPass").build();
        UserPrincipal resp = UserPrincipal.create(member);

        Assertions.assertThat(resp.getUsername()).isEqualTo("TestUser");
    }

    @Test
    void getPassword() {
        String resp = userDetails.getPassword();
        assertEquals("testPass", resp);
    }

    @Test
    void isAccountNonExpired() {
        boolean resp = userDetails.isAccountNonExpired();
        assertTrue(resp);
    }

    @Test
    void isAccountNonLocked() {
        boolean resp = userDetails.isAccountNonLocked();
        assertTrue(resp);
    }

    @Test
    void isCredentialsNonExpired() {
        boolean resp = userDetails.isCredentialsNonExpired();
        assertTrue(resp);
    }

    @Test
    void isEnabled() {
        boolean resp = userDetails.isEnabled();
        assertTrue(resp);
    }
}