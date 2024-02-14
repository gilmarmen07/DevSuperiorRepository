package com.devsuperior.DSCatalog.services;

import com.devsuperior.DSCatalog.dto.EmailDTO;
import com.devsuperior.DSCatalog.dto.NewPasswordDTO;
import com.devsuperior.DSCatalog.entities.PasswordRecover;
import com.devsuperior.DSCatalog.entities.User;
import com.devsuperior.DSCatalog.repositories.PasswordRecoverRepository;
import com.devsuperior.DSCatalog.repositories.UserRepository;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createRecoveryToken(EmailDTO emailDTO) {

        User user = userRepository.findByEmail(emailDTO.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Email not found");
        }
        String token = UUID.randomUUID().toString();
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setEmail(emailDTO.getEmail());
        passwordRecover.setToken(token);
        passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        passwordRecoverRepository.save(passwordRecover);

        String body = "click on the link to set a new password\n\n" +
                recoverUri + token + ". Validity of " + tokenMinutes + " minutes";
        emailService.sendEmail(emailDTO.getEmail(), "Password recovery", body);
    }

    @Transactional
    public void saveNewPassword(NewPasswordDTO newPasswordDTO) {
        List<PasswordRecover> passwordRecovers = passwordRecoverRepository.searchValidTokens(newPasswordDTO.getToken(), Instant.now());
        if (passwordRecovers.size() == 0) {
            throw new ResourceNotFoundException("Invalid token");
        }
        User user = userRepository.findByEmail(passwordRecovers.get(0).getEmail());
        user.setPassword(passwordEncoder.encode(newPasswordDTO.getPassword()));
        userRepository.save(user);
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }
}
