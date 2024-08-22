package com.backend.carhub.SecurityConfig;

import com.backend.carhub.Service.JwtService;
import com.backend.carhub.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userService;

    JwtFilter(JwtService jwtService, UserDetailsService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filter initialised");
        System.out.println("request.getHeader(\"Authorization\") :-"+request.getHeader("Authorization"));
        if(request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization");

            if(token.startsWith("Bearer ")) {
                token = token.substring(7);
                System.out.println(token);
                if(this.jwtService.extractUsername(token).isEmpty()){
                    throw new RuntimeException("Invalid token");
                }
                var userEmail = this.jwtService.extractUsername(token);
//                var user = this.userService.getUserByEmail(userEmail);
//                System.out.println(user.getUserName()+" "+user.getEmail());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (userEmail != null && authentication == null) {
                    UserDetails userDetails = this.userService.loadUserByUsername(userEmail);

                    if (jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }




            }
        }


    }
        filterChain.doFilter(request, response);
}}
