package com.punnawat.backend.api.dtos.request;

import lombok.Data;

@Data
public class LoginDTORequest {
    private String email;
    private String password;
}
