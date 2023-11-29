package com.telkomsel.fww.integrator.security;

import com.telkomsel.fww.integrator.domain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String userName;
    private String phone;
    private String company;
    private String firstName;
    private String lastName;

    private Collection<? extends GrantedAuthority> authorities;
    private transient Map<String, Object> attributes;

    public UserPrincipal(Member user, Collection<? extends GrantedAuthority> authorities) {
        email = user.getEmail();
        password = user.getPassword();
        this.authorities = authorities;
        userName = user.getUsername();
        phone = user.getPhone();
        firstName = user.getFirstName();
        lastName = user.getLastName();
    }

    public static UserPrincipal create(Member user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(user, authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
