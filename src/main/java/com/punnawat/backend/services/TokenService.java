package com.punnawat.backend.services;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import com.punnawat.backend.api.dtos.response.TokenResponse;
import com.punnawat.backend.entity.User;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class TokenService {
  @Value("${app.token.secret}")
  private String secret;

  @Value("${app.token.issuer}")
  private String issuer;

  @Value("${app.token.expiration-time-ms}")
  private long expirationTimeMillis;

  // The logic is now just one line!
  public Algorithm getAlgorithm() {
    return Algorithm.HMAC256(secret);
  }

  public TokenResponse tokenize(User user) {
    String token = "";
    Date expiresAt = null;
    try {
      Algorithm algorithm = getAlgorithm();
      long expirationTimeMillis = System.currentTimeMillis() + this.expirationTimeMillis;
      expiresAt = new Date(expirationTimeMillis);
      token = JWT.create()
          .withIssuer(this.issuer)
          .withExpiresAt(expiresAt)
          .withClaim("principal", user.getId().toString())
          .withClaim("role", "USER")
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
    }
    return new TokenResponse(token, expiresAt);
  }

  public DecodedJWT verify(String token) {
    DecodedJWT decodedJWT;
    try {
      Algorithm algorithm = getAlgorithm();
      JWTVerifier verifier = JWT.require(algorithm)
          // specify any specific claim validations
          .withIssuer(this.issuer)
          .build();
      decodedJWT = verifier.verify(token);
    } catch (JWTVerificationException exception) {
      // Invalid signature/claims
      return null;
    }
    return decodedJWT;
  }
}
