package com.punnawat.backend.mapper;

import com.punnawat.backend.api.dtos.response.LoginDTOResponse;
import com.punnawat.backend.api.dtos.response.RegisterDTOResponse;
import com.punnawat.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterDTOResponse toRegisterResponse(User user);
}
