package com.telkomsel.fww.integrator.controller;

import com.telkomsel.fww.integrator.common.ResponseCode;
import com.telkomsel.fww.integrator.payload.request.RequestLogin;
import com.telkomsel.fww.integrator.payload.request.RequestRegister;
import com.telkomsel.fww.integrator.payload.response.ResponseLogin;
import com.telkomsel.fww.integrator.service.AuthorizationService;
import com.telkomsel.fww.integrator.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("v1/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody RequestLogin requestLogin) {
        ResponseLogin resp = authorizationService.processLogin(requestLogin);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.OK, resp);
    }

    @PostMapping("v1/auth/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RequestRegister requestRegister) {
        authorizationService.processRegister(requestRegister);
        return ResponseHandler.generateResponse(ResponseCode.SUCCESS,
                HttpStatus.NO_CONTENT, "");
    }
}
