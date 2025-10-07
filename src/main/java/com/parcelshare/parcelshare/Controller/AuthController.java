package com.parcelshare.parcelshare.Controller;


import com.parcelshare.parcelshare.Config.JwtServices;
import com.parcelshare.parcelshare.Dto.LoginResponseDTO;
import com.parcelshare.parcelshare.Dto.UserDto;
import com.parcelshare.parcelshare.Model.Userss;
import com.parcelshare.parcelshare.Repository.UsersRepo;
import com.parcelshare.parcelshare.Service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/")


public class AuthController {


    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
    private final UsersRepo usersRepo;
    private final UserServices userServices;


    @GetMapping("/react")
    public String reacttest(){

        return "Hello World!";
    }
    @PostMapping("/register")
    public String register(@RequestBody Userss  userss){
        if(usersRepo.findByUsername(userss.getUsername())!=null){
            throw new RuntimeException("Username already exists");
        }
        userss.setPassword(passwordEncoder.encode(userss.getPassword()));
        usersRepo.save(userss);

       return "User registered successfully for = "+userss.getUsername();

     }

     @PostMapping("/login")
     public LoginResponseDTO login(@RequestBody UserDto users){

         Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken( users.getUsername(), users.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);
         LoginResponseDTO  responseDTO = new LoginResponseDTO();
         responseDTO.setToken( jwtServices.generateToken(authentication));
         responseDTO.setRole(jwtServices.getRolebyToken(responseDTO.getToken()));
         responseDTO.setUserid(userServices.getCurrentUserid(jwtServices.getUsernameFromToken(responseDTO.getToken())));
       return  responseDTO;
     }


}
