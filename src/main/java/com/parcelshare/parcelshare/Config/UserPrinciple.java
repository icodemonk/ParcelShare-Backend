package com.parcelshare.parcelshare.Config;

import com.parcelshare.parcelshare.Model.Userss;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class UserPrinciple implements UserDetails {

    private Userss userss;

    public UserPrinciple(){}
    public UserPrinciple(Userss userss) {
        this.userss = userss;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(userss.getRole().name())) ;
    }

    @Override
    public String getPassword() {
        return userss.getPassword();
    }

    @Override
    public String getUsername() {
        return userss.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
