package com.gradu.infou.Auth.Entity;

import com.gradu.infou.Domain.Entity.Enum.Role;
import com.gradu.infou.Domain.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = Role.valueOf(user.getRole());

        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getAuthId();
    }

    @Override
    public String getUsername() {
        return user.getAuthId();
    }

    // 계정이 만료 되었는지 (true: 만료 X)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 (ture: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되었는지 (true: 만료X)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return false;
    }
}
