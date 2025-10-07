package com.parcelshare.parcelshare.Config;

import com.parcelshare.parcelshare.Repository.UsersRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final UsersRepo  usersRepo;
    private final CustomUserDetailService customUserDetailService;



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        final String authHeader = request.getHeader("Authorization");
        final String authHeader = request.getHeader("Authorization");
        String token;

        String path = request.getServletPath();
        if ("/api/auth/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }




        if ( authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }

        assert authHeader != null;
        token=authHeader.substring(7);

        if (!jwtServices.validateToken(token)) {
            filterChain.doFilter(request,response);
        }

        String username = jwtServices.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            if (jwtServices.userValidation(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request,response);
            }





        }






    }
}
