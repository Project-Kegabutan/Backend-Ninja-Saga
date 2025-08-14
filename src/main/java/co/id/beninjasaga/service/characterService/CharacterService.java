package co.id.beninjasaga.service.characterService;

import co.id.beninjasaga.model.dto.CharacterListDto;
import co.id.beninjasaga.repository.AccountsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class CharacterService {

    @Autowired
    private AccountsRepository accountsRepository;
    public Map<String,Object> getCharactersList(CharacterListDto.Request request) throws Exception {
        log.info("[AMF] Start Get CharacterList - Session Key : {}",request.getSessionKey());
        Map<String,Object> m = new LinkedHashMap<>();
        CharacterListDto.Response response = new CharacterListDto.Response();
        try {
            response.setStatus("1");
            response.setLogin_per_day("1");
            response.setResult(new ArrayList<>());

            m.put("login_per_day", '1');
            m.put("status", "1");
            m.put("result", response.getResult());
            return m;
        }catch (Exception e){

        }
        return m;
    }
}
