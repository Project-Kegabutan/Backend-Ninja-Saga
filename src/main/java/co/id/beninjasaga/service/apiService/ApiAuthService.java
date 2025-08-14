package co.id.beninjasaga.service.apiService;

import co.id.beninjasaga.model.dto.api.AuthDto;
import co.id.beninjasaga.model.entity.AccountsEntity;
import co.id.beninjasaga.model.entity.AccountsCharacterTokenEntity;
import co.id.beninjasaga.repository.AccountsRepository;
import co.id.beninjasaga.repository.CharacterTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ApiAuthService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CharacterTokenRepository characterTokenRepository;

    @Transactional
    public AuthDto.ResponseRegistration accountRegistration(String username, String password, String email) {
        AuthDto.ResponseRegistration response = new AuthDto.ResponseRegistration();
        try {
            Optional<AccountsEntity> checkEmail = accountsRepository.findByEmail(email);
            Optional<AccountsEntity> checkUsername = accountsRepository.findByUsername(username);
            if (checkUsername.isPresent()) {
                response.setSuccess(false);
                response.setPayload(null);
                response.setResponseCode("4090200");
                response.setResponseMessage("Username Already Exist");
            }else if (checkEmail.isPresent()){
                response.setSuccess(false);
                response.setPayload(null);
                response.setResponseCode("4090200");
                response.setResponseMessage("Email Already Exist");
            }else{
                AccountsEntity acc = new AccountsEntity();
                acc.setUsername(username);
                acc.setPassword(password);
                acc.setEmail(email);
                acc.setAccountType(1);
                acc.setCreatedAt(LocalDateTime.now());
                acc.setUpdatedAt(LocalDateTime.now());
                acc = accountsRepository.save(acc); // persist dulu, supaya punya accountId

                AccountsCharacterTokenEntity token = new AccountsCharacterTokenEntity();
                token.setAccount(acc); // link FK
                token.setUsername(username);
                token.setBalanceToken(0);
                token.setCreatedAt(LocalDateTime.now());
                token.setUpdatedAt(LocalDateTime.now());
                characterTokenRepository.save(token);

                // optional: kalau mau reflect ke relasi dua arah
                acc.setCharacterToken(token);

                Map<String, Object> data = new HashMap<>();
                data.put("username", username);
                data.put("email", email);

                response.setSuccess(true);
                response.setPayload(data);
                response.setResponseCode("2000200");
                response.setResponseMessage("[Success] : Account Registration");

            }
        }catch (Exception e){
            return null;
        }
        return response;
    }
}
