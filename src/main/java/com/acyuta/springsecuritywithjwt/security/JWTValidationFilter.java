package com.acyuta.springsecuritywithjwt.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class JWTValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwt = parseJwt(httpServletRequest.getHeader("Authorization"));

            if (jwt != null) {

                var secret = Keys.hmacShaKeyFor("this is a large secret key.this is a large secret key.".getBytes(StandardCharsets.UTF_8));

                var claims = Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                var username = String.valueOf(claims.get("username"));
                var authorities = obtainAuthorities(String.valueOf(claims.get("authorities")));

                var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Error while validating jwt {}", e.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private String parseJwt(String jwtWithBearer) {
        if (StringUtils.hasText(jwtWithBearer) && jwtWithBearer.startsWith("Bearer "))
            return jwtWithBearer.substring(7);
        return null;
    }

    private Collection<? extends GrantedAuthority> obtainAuthorities(String authorityString) {
        var authorities = new ArrayList<GrantedAuthority>();
        Arrays.stream(authorityString.split(","))
                .forEach(i -> authorities.add(new SimpleGrantedAuthority(i)));
        return authorities;
    }
}
