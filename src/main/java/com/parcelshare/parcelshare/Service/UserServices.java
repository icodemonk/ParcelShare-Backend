package com.parcelshare.parcelshare.Service;

import com.parcelshare.parcelshare.Config.JwtServices;
import com.parcelshare.parcelshare.Config.UserPrinciple;
import com.parcelshare.parcelshare.Dto.UserDto;
import com.parcelshare.parcelshare.Model.Userss;
import com.parcelshare.parcelshare.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    JwtServices jwtServices;

    @Autowired
    UsersRepo userRepo;

    public int getCurrentUserid(String Username) {
        Userss users = userRepo.findByUsername(Username);
        int id = users.getId();
        return id;
    }
}