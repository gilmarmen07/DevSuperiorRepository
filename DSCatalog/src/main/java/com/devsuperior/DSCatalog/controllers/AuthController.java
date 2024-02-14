package com.devsuperior.DSCatalog.controllers;

import com.devsuperior.DSCatalog.dto.EmailDTO;
import com.devsuperior.DSCatalog.dto.NewPasswordDTO;
import com.devsuperior.DSCatalog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "recover-token")
    public ResponseEntity<Void> createRecoveryToken(@Valid @RequestBody EmailDTO emailDTO){
        authService.createRecoveryToken(emailDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO){
        authService.saveNewPassword(newPasswordDTO);
        return ResponseEntity.noContent().build();
    }
}
