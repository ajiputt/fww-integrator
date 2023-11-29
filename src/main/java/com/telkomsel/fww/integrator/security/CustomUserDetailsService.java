package com.telkomsel.fww.integrator.security;

import com.telkomsel.fww.integrator.domain.Member;
import com.telkomsel.fww.integrator.feign.service.MemberClientService;
import com.telkomsel.fww.integrator.feign.service.MemberTokenClientService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberClientService memberClientService;

    private final MemberTokenClientService memberTokenClientService;

    public CustomUserDetailsService(MemberClientService memberClientService, MemberTokenClientService memberTokenClientService) {
        this.memberClientService = memberClientService;
        this.memberTokenClientService = memberTokenClientService;
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            Member user = memberClientService.getMemberByUsername(username);
            return UserPrincipal.create(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Users not found with " +
                    "username  : " + username);
        }
    }

    public boolean isTokenValid(String username, String token) {
        return memberTokenClientService.existsByUsernameAndToken(username, token);
    }
}