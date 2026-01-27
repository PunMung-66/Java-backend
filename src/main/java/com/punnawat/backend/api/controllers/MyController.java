package com.punnawat.backend.api.controllers;

import com.punnawat.backend.api.dtos.request.LoginDTORequest;
import com.punnawat.backend.api.dtos.request.RegisterDTORequest;
import com.punnawat.backend.api.dtos.response.LoginDTOResponse;
import com.punnawat.backend.api.dtos.response.RegisterDTOResponse;
import com.punnawat.backend.api.response.ApiResponse;
import com.punnawat.backend.entity.User;
import com.punnawat.backend.expections.BaseException;
import com.punnawat.backend.expections.FileException;
import com.punnawat.backend.expections.ProductException;
import com.punnawat.backend.business.ProductBusiness;
import com.punnawat.backend.business.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class MyController {
    private final UserBusiness userBusiness;
    private final ProductBusiness productBusiness;

    @Autowired
    public MyController(UserBusiness userBusiness, ProductBusiness productBusiness){
        this.userBusiness = userBusiness;
        this.productBusiness = productBusiness;
    }

    @GetMapping
    public String apiAvailable(){
        return "API runnnig...";
    }

    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse<RegisterDTOResponse>> register(@RequestBody RegisterDTORequest registerDTORequest) throws BaseException {
        RegisterDTOResponse response = userBusiness.register(registerDTORequest);
        return ResponseEntity.ok(new ApiResponse<>("success", response));
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse<LoginDTOResponse>> login(@RequestBody LoginDTORequest loginDTORequest) throws BaseException {
        LoginDTOResponse response = userBusiness.login(loginDTORequest);
        return ResponseEntity.ok(new ApiResponse<>("success", response));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<String> findProductById(@PathVariable String id) throws ProductException {
        String response = productBusiness.findProductById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public  ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws FileException {
        String response = userBusiness.uploadProfilePicture(file);
        return ResponseEntity.ok("Got file");
    }
}
