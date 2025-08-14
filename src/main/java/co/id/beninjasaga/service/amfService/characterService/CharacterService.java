package co.id.beninjasaga.service.amfService.characterService;

import co.id.beninjasaga.model.dto.CharacterListDto;
import co.id.beninjasaga.model.dto.CreateCharacterDto;
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
        Map<String,Object> result = new LinkedHashMap<>();
        CharacterListDto.Response response = new CharacterListDto.Response();
        List<Object[]> rows;
        try {
            Optional<AccountsEntity> checkSession = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (checkSession == null){
                response.setStatus("0");
                result.put("status", response.getStatus());
                result.put("error", "Ngapain Mas?");
                return result;
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
                result.put("login_per_day", response.getLogin_per_day());
                result.put("status", response.getStatus());
                result.put("result", response.getResult());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("[AMF] End Get CharacterList - Session Key : {}",request.getSessionKey());
        return result;
    }

    public Map<String, Object> createCharacter(CreateCharacterDto.Request request) throws Exception{
        log.info("[AMF] Start Create Character - Session Key : {}",request.getSessionKey());
        log.info("[AMF] Request : {}",request.toString());
        log.info("[AMF] Request - Character Name : {} | Character Gender : {} | Character Skin Color : {} | Character Face : {} | Character Hair : {} | Character Hair Color : {}",
                request.getCharacterName(),
                request.getCharacterGender(),
                request.getCharacterSkinColor(),
                request.getCharacterFace(),
                request.getCharacterHair(),
                request.getCharacterHairColor());
        //Character Name : Hello | Character Gender : 0 | Character Skin Color : 11.0 | Character Face : 01_0 | Character Hair : 01_0 | Character Hair Color : 5.0
        Map<String,Object> result = new LinkedHashMap<>();
        CreateCharacterDto.Response response = new CreateCharacterDto.Response();
        try {
            Optional<AccountsEntity> checkSession = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (checkSession == null){
                response.setStatus("0");
                result.put("status", response.getStatus());
                result.put("error", "Ngapain Mas?");
                return result;
            }else {
                response.setStatus("1");
                response.setCharacterName(request.getCharacterName());
                response.setCharacterId(checkSession.get().getAccountId());
                response.setAccountId(checkSession.get().getAccountId());
                response.setMessage("Character creation completed successfully");

                result.put("status", response.getStatus());
                result.put("character_id", response.getAccountId());
                result.put("character_name", response.getCharacterName());
                result.put("account_id", response.getCharacterId());
                result.put("message", response.getMessage());


                return result;

            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
