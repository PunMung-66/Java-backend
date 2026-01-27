package com.punnawat.backend.business;

import com.punnawat.backend.api.dtos.request.LoginDTORequest;
import com.punnawat.backend.api.dtos.request.RegisterDTORequest;
import com.punnawat.backend.api.dtos.response.LoginDTOResponse;
import com.punnawat.backend.api.dtos.response.RegisterDTOResponse;
import com.punnawat.backend.entity.User;
import com.punnawat.backend.expections.BaseException;
import com.punnawat.backend.expections.FileException;
import com.punnawat.backend.expections.UserException;
import com.punnawat.backend.mapper.UserMapper;
import com.punnawat.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class UserBusiness {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserBusiness(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public RegisterDTOResponse register(RegisterDTORequest request) throws BaseException {
        if (request == null) {
            throw UserException.requestNull();
        }
        if (request.getEmail() == null) {
            throw UserException.requestEmailNull();
        }
        if (request.getPassword() == null) {
            throw UserException.requestPasswordNull();
        }
        if (request.getName() == null) {
            throw UserException.requestNameNull();
        }
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());
        return userMapper.toRegisterResponse(user);
    }

    public LoginDTOResponse login(LoginDTORequest request) throws BaseException {
        if (request == null) {
            throw UserException.requestNull();
        }
        if (request.getEmail() == null) {
            throw UserException.requestEmailNull();
        }
        if (request.getPassword() == null) {
            throw UserException.requestPasswordNull();
        }
        String message = userService.verifyLogin(request.getEmail(), request.getPassword());
        return new LoginDTOResponse(LocalDateTime.now(), message);
    }

    public String uploadProfilePicture(MultipartFile file) throws FileException {
        if ( file == null){
            throw FileException.isNull();
        }
        if ( file.getSize() > 1048576 * 2){
            throw FileException.fileTooLarge();
        }
        String contentType = file.getContentType();
        if ( contentType == null ){
            throw FileException.contentTypeMissing();
        }
        List<String> supportType = Arrays.asList("image/jpeg", "image/png");
        if ( !supportType.contains(contentType)){
            throw FileException.unsupportedType();
        }

        return "uplead-success";
    }
}
