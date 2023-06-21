package com.gaurav.springcloudproject.configuration.filter;
import com.gaurav.springcloudproject.authentication.CustomUserDetailsService;
import com.gaurav.springcloudproject.authentication.model.UserDetailImpl;
import com.gaurav.springcloudproject.exception.ProcessNotAllowedException;
import com.gaurav.springcloudproject.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Slf4j

public class AuthTokenFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtils jwtUtils;
    @Value("${blogUser.jwtSecret}")
    private String jwtSecret;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String username = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                username = jwtUtils.extractUsername(authorizationHeader.substring(7));
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetailImpl userDetails = (UserDetailImpl) userDetailsService.loadUserByUsername(username);
                if (Boolean.TRUE.equals(jwtUtils.validateToken(authorizationHeader.substring(7), userDetails))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw new ProcessNotAllowedException("Token is invalid.");
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

    }
}
