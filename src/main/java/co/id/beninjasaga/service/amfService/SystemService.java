package co.id.beninjasaga.service.amfService;
import co.id.beninjasaga.model.dto.LoginDto;
import co.id.beninjasaga.model.entity.AccountsEntity;
import co.id.beninjasaga.repository.AccountsRepository;
import co.id.beninjasaga.util.HashUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SystemService {

    @Autowired
    private AccountsRepository accountsRepository;

    private static final Logger log = LoggerFactory.getLogger(SystemService.class);

    @Transactional
    public Map<String,Object> login(LoginDto.Request request) throws Exception{
        log.info("[AMF] Start Login - Username : {} | Password : {}",request.getUsername(), request.getPassword());

        Map<String,Object> m = new LinkedHashMap<>();
        AccountsEntity getAcc;
        try {
            getAcc = accountsRepository.findByUsername(request.getUsername()).orElse(null);
            if (getAcc == null){
                m.put("status", 0);
                m.put("error", " - Account not found");
                return m;
            }
            if (!request.getPassword().equals(getAcc.getPassword())){
                m.put("status", 0);
                m.put("error", " - Invalid Password");
                return m;
            }
            // result array tanpa key: [25, 0, 100, <session>]
            String session = java.util.UUID.randomUUID().toString().replace("-", "");
            java.util.List<Object> result = new java.util.ArrayList<>();
            result.add(getAcc.getAccountId().intValue()); result.add(getAcc.getAccountType()); result.add(getAcc.getCharacterToken().getBalanceToken()); result.add(session);

            // signature sesuai HashUtil::getArrayHash (PHP)
            String signature = HashUtil.getArrayHash(result, session);
            int updateData = accountsRepository.updateSessionKey(session, getAcc.getAccountId(), LocalDateTime.now());
            log.info("[AMF] Update Session Username {} Result : {}",request.getUsername(),updateData);
            log.info("[AMF] Success Login ID {}",getAcc.getAccountId());
            m.put("status", 1);
            m.put("result", result);
            m.put("signature", signature);
            return m;
        }catch (Exception e){
            m.put("status", 0);
            m.put("error", e.getMessage());
            return m;
        }
    }
}
