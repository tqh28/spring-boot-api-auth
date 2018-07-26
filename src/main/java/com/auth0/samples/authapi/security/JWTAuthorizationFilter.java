package com.auth0.samples.authapi.security;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.samples.authapi.constant.SecurityConstants;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String tokenHeader = request.getHeader(SecurityConstants.HEADER_STRING);
        
        if (tokenHeader != null && tokenHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(tokenHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String user = Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes())
                .parseClaimsJws(tokenHeader.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody().getSubject();
        
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new LinkedList<>());
        }
        
        return null;
    }
}
