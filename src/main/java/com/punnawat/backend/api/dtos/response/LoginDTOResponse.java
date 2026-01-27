package com.punnawat.backend.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class LoginDTOResponse {
    private LocalDateTime timestamp;
    private String message;
}
