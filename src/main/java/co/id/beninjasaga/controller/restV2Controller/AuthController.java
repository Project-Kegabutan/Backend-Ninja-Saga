package co.id.beninjasaga.controller.restV2Controller;

import co.id.beninjasaga.model.dto.api.AuthDto;
import co.id.beninjasaga.repository.AccountsRepository;
import co.id.beninjasaga.service.apiService.ApiAuthService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/ninjasaga")
@Slf4j
public class AuthController {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private ApiAuthService apiAuthService;

    @PostMapping("/registration")
    public ResponseEntity<?> authenticateUserPortal(@RequestBody AuthDto.RequestRegistration request) throws Exception {
        String username;
        String password;
        String email;
        Gson gson = new GsonBuilder().create();
        try {
            username = request.getUsername();
            email = request.getEmail();
            password = request.getPassword();
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("isSuccess", false, "message", "Username is required"));
            }
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("isSuccess", false, "message", "Email is required"));
            }
            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("isSuccess", false, "message", "Password is required"));
            }


            AuthDto.ResponseRegistration registrationRS =  apiAuthService.accountRegistration(username, password, email);
            return new ResponseEntity<>(registrationRS,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Registration Failed");
            throw new Exception("401");
        }
    }
}
