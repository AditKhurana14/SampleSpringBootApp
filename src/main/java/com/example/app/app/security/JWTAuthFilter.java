package com.example.app.app.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTAuth jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private static final org.slf4j.Logger logger=  LoggerFactory.getLogger(JWTAuthFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
           final String authHeader = request.getHeader("Authorization");
           final String jwt;
           final String username;

           if(authHeader==null || !authHeader.startsWith("Bearer ")){
               logger.info("No Authorization header or it doesn't start with Bearer");
               filterChain.doFilter(request, response);
               return;


           }
        jwt=authHeader.substring(7);
        username=jwtService.getUserName(jwt);
        logger.info("Extracted username: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                boolean valid = jwtService.isTokenValid(username, userDetails, jwt);

                if(valid){
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);




                }




            }
            logger.info("Auth: " + SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);







        }
        catch (JwtException | UsernameNotFoundException ex){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("Error"+" "+ ex.getMessage());


    }
}}
