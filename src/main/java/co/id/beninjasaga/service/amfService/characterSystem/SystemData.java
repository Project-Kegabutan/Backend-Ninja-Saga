package co.id.beninjasaga.service.amfService.characterSystem;

import co.id.beninjasaga.model.dto.GetCreateCharacterDto;
import co.id.beninjasaga.model.dto.LoginDto;
import co.id.beninjasaga.model.entity.AccountsEntity;
import co.id.beninjasaga.repository.AccountsRepository;
import co.id.beninjasaga.repository.CharacterListRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemData {

    private final AccountsRepository accountsRepository;

    private final CharacterListRepository characterListRepository;

    public Map<String,Object> getCreateCharacter(GetCreateCharacterDto.Request request) throws Exception{
        log.info("[AMF] Start Get Create Character - Session Key : {}", request.getSessionKey());
        log.info("[AMF] Start Get Create Character - Test Version : {}", request.getTestVersion());

        Map<String, Object> response = new LinkedHashMap<>();
        try {
            var optAcc = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (optAcc.isEmpty()) {
                response.put("status", 0);
                response.put("error", "Invalid session key");
                return response;
            }
            var mapOpt = characterListRepository.findNewCreatingCharacter(optAcc.get().getAccountId());
            if (mapOpt.isEmpty()) { /* handle not found */ }
            Map<String,Object> row = mapOpt.get();

            JSONObject newInfoCharacter = new JSONObject(row);
            log.info("newInfoCharacter {}",newInfoCharacter.toString());
            Map<String, Object> characterInformation = new LinkedHashMap<>();
            characterInformation.put("character_id", newInfoCharacter.get("character_id"));
            characterInformation.put("character_name", newInfoCharacter.get("character_name"));
            characterInformation.put("character_gender", newInfoCharacter.get("gender_str"));
            characterInformation.put("character_level", newInfoCharacter.get("character_level"));
            characterInformation.put("test_version", false);

            response.put("status", 1);
            response.put("character", characterInformation);
            response.put("message", "Get Character creation completed successfully");
        }catch (Exception e){
            response.put("status", 0);
            response.put("message", e.getMessage());

            e.printStackTrace();
        }
        return response;
    }
}
