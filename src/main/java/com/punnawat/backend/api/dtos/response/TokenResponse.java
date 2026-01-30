package com.punnawat.backend.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor // Generates a constructor for all fields
public class TokenResponse {
  private String token;
  private Date expiresAt;
}
