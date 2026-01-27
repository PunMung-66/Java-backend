package com.punnawat.backend.api.dtos.request;

import lombok.*;

//@Getter
//@Setter
@Data
public class RegisterDTORequest {
    private String email;
    private String name;
    private String password;
}

