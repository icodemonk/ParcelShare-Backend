package com.parcelshare.parcelshare.Config;

import com.parcelshare.parcelshare.Model.Userss;
import com.parcelshare.parcelshare.Repository.UsersRepo;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UsersRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userss userss =repository.findByUsername(username);
        if(userss == null){
            throw new UsernameNotFoundException(username);
        }
         return new UserPrinciple(userss);
    }
}
