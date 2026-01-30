package com.punnawat.backend.config.Token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.punnawat.backend.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component // 👈 Add this annotation
public class TokenFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  public TokenFilter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");

    // 1. If no Bearer token, just move to the next filter
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // 2. Extract and verify token
    String token = authorizationHeader.substring(7);
    DecodedJWT decodedJWT = tokenService.verify(token);

    if (decodedJWT != null) {
      // 3. Extract claims
      String principal = decodedJWT.getClaim("principal").asString();
      String role = "ROLE_" + decodedJWT.getClaim("role").asString(); // need to use ROLE_ prefix

      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      if (role != null) {
        authorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
        System.out.println("User: " + principal + " | Authorities: " + authorities);
      }

      // 4. Create Authentication object
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null,
          authorities);

      // 5. Set the security context (Log the user in)
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 6. Continue the chain
    filterChain.doFilter(request, response);
  }
}