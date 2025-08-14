package co.id.beninjasaga.service.characterService;

import co.id.beninjasaga.model.dto.CharacterListDto;
import co.id.beninjasaga.model.entity.AccountsEntity;
import co.id.beninjasaga.repository.AccountsRepository;
import co.id.beninjasaga.repository.CharacterListRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CharacterService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    CharacterListRepository characterListRepository;
    @Transactional
    public Map<String,Object> getCharactersList(CharacterListDto.Request request) throws Exception {
        log.info("[AMF] Start Get CharacterList - Session Key : {}",request.getSessionKey());
        Map<String,Object> m = new LinkedHashMap<>();
        CharacterListDto.Response response = new CharacterListDto.Response();
        List<Object[]> rows;
        try {
            Optional<AccountsEntity> checkSession = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (checkSession == null){
                response.setStatus("0");
                m.put("status", response.getStatus());
                m.put("error", "Ngapain Mas?");
                return m;
            }else {
                rows = characterListRepository.findLiteByAccountId(checkSession.get().getAccountId());
                List<List<Object>> characterList = new ArrayList<>(rows.size());
                for (Object[] r : rows) {
                    Long id           = ((Number) r[0]).longValue();
                    String name       = (String) r[1];
                    Integer level     = ((Number) r[2]).intValue();
                    String genderStr  = r[3] != null ? r[3].toString() : null;

                    characterList.add(List.of(id, name, level, genderStr));
                }
                accountsRepository.incrementLoginPerDay(checkSession.get().getAccountId());
                Optional<AccountsEntity> dataAccounts = accountsRepository.findByUsername(checkSession.get().getUsername());
                response.setStatus("1");
                response.setLogin_per_day(dataAccounts.get().getLoginPerDay().toString());
                response.setResult(characterList);
                m.put("login_per_day", response.getLogin_per_day());
                m.put("status", response.getStatus());
                m.put("result", response.getResult());
                return m;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }
}
