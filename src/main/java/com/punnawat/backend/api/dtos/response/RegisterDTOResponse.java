package com.punnawat.backend.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterDTOResponse {
    private String email;
    private String name;
}

