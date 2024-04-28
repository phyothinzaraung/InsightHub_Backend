package edu.miu.cs489.InsightHub.config;

import edu.miu.cs489.InsightHub.service.UserService;
import edu.miu.cs489.InsightHub.util.Constants;
import edu.miu.cs489.InsightHub.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(antPathMatcher.match(Constants.API_VERSION + "/user/**", uri)){
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            throw new BadCredentialsException("Invalid JWT header");
        }
        String token = header.substring(7);
        String email = jwtUtil.extractUsername(token);
        if(email == null){
            throw new BadCredentialsException("Invalid JWT token");
        }
        UserDetails user = userService.loadUserByUsername(email);
        if(user == null){
            throw new BadCredentialsException("Invalid JWT token");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
