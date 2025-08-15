package co.id.beninjasaga.service.amfService.characterService;

import co.id.beninjasaga.model.dto.CharacterListDto;
import co.id.beninjasaga.model.dto.CreateCharacterDto;
import co.id.beninjasaga.model.dto.DeleteCharacterDto;
import co.id.beninjasaga.model.entity.*;
import co.id.beninjasaga.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    // Inject komponen/Repo saja (bukan entity)
    private final JdbcTemplate jdbc;
    private final AccountsRepository accountsRepository;
    private final CharacterListRepository characterListRepository;
    private final CharacterElementPointRepository characterElementPointRepository;
    private final CharacterBodyStyleRepository characterBodyStyleRepository;
    private final CharacterEquippedBodySetRepository characterEquippedBodySetRepository;
    private final CharacterEquippedWeaponRepository characterEquippedWeaponRepository;
    private final CharacterEquippedSkillsRepository characterEquippedSkillsRepository;
    private final CharacterItemRepository characterItemRepository;
    private final CharacterEquippedBackItemRepository characterEquippedBackItemRepository;
    private final CharacterEquippedAccessoryRepository characterEquippedAccessoryRepository;
    // ==== GET LIST ====
    @Transactional
    public Map<String, Object> getCharactersList(CharacterListDto.Request request) {
        log.info("[AMF] Start Get CharacterList - Session Key : {}", request.getSessionKey());
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            var optAcc = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (optAcc.isEmpty()) {
                result.put("status", "0");
                result.put("error", "Invalid session key");
                return result;
            }
            AccountsEntity acc = optAcc.get();

            List<Object[]> rows = characterListRepository.findLiteByAccountId(acc.getAccountId());
            List<List<Object>> characterList = new ArrayList<>(rows.size());
            for (Object[] r : rows) {
                Long id = ((Number) r[0]).longValue();
                String name = (String) r[1];
                Integer level = ((Number) r[2]).intValue();
                String genderStr = (r[3] != null) ? r[3].toString() : null;
                characterList.add(List.of(id.intValue(), name, level.intValue(), genderStr));
            }

            accountsRepository.incrementLoginPerDay(acc.getAccountId());
            String loginPerDay = accountsRepository.findById(acc.getAccountId())
                    .map(a -> String.valueOf(a.getLoginPerDay()))
                    .orElse("0");

            result.put("status", "1");
            result.put("login_per_day", loginPerDay);
            result.put("result", characterList);
            return result;
        } catch (Exception e) {
            log.error("[AMF] Get CharacterList error", e);
            result.put("status", "0");
            result.put("error", "Internal error");
            return result;
        } finally {
            log.info("[AMF] End Get CharacterList - Session Key : {}", request.getSessionKey());
        }
    }

    // ==== CREATE ====
    @Transactional
    public Map<String, Object> createCharacter(CreateCharacterDto.Request request) {
        log.info("[AMF] Start Create Character - Session Key : {}", request.getSessionKey());
        log.info("[AMF] Start Create Character - character Name : {}", request.getCharacterName());
        log.info("[AMF] Start Create Character - character Gender : {}", request.getCharacterGender());
        log.info("[AMF] Start Create Character - Character Hair : {}", request.getCharacterHair());
        log.info("[AMF] Start Create Character - Character Hair Color : {}", request.getCharacterHairColor());
        log.info("[AMF] Start Create Character - Character Skin Color : {}", request.getCharacterSkinColor());
        log.info("[AMF] Start Create Character - Character Face : {}", request.getCharacterFace());

        Map<String, Object> result = new LinkedHashMap<>();
        try {
            var optAcc = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (optAcc.isEmpty()) {
                result.put("status", "0");
                result.put("error", "Invalid session key");
                return result;
            }
            AccountsEntity account = optAcc.get();

            //Saved Character List
            CharacterListEntity ch = new CharacterListEntity();
            ch.setAccount(account);
            ch.setCharacterName(request.getCharacterName());
            ch.setCharacterGender(toIntSafe(request.getCharacterGender(), 0));
            ch.setCharacterLevel(1);
            ch.setCharacterRank(1);
            ch.setCharacterExp("130");
            ch.setCharacterGold(0);
            ch.setCharacterHp(100);
            ch.setCharacterMaxHp(100);
            ch.setCharacterCp(100);
            ch.setCharacterMaxCp(100);
            ch.setCharacterSkillType("");
            ch.setCreatedAt(LocalDateTime.now());
            ch = characterListRepository.save(ch);

            //Saved Character Body Style
            CharacterBodyStyleEntity cbs = new CharacterBodyStyleEntity();
            cbs.setCharacter(ch);
            cbs.setCharacterHairStyle(request.getCharacterHair());
            cbs.setCharacterHairColorStyle(request.getCharacterHairColor());
            cbs.setCharacterFace(request.getCharacterFace());
            cbs.setCharacterSkinColor(request.getCharacterSkinColor());
            characterBodyStyleRepository.save(cbs);

            //Saved Character Element Point
            CharacterElementPointEntity ce = new CharacterElementPointEntity();
            ce.setCharacter(ch);
            ce.setCharacterSkillAp(0);
            ce.setFire(0);
            ce.setWater(0);
            ce.setWind(0);
            ce.setEarth(0);
            ce.setLightning(0);
            characterElementPointRepository.save(ce);

            //Saved Character Equipped Body Set
            CharacterEquippedBodySetEntity bodySet = new CharacterEquippedBodySetEntity();
            bodySet.setCharacter(ch);
            bodySet.setCharacterEquippedBodySetNumber("set1");
            characterEquippedBodySetRepository.save(bodySet);

            //Saved Character Equipped Weapon
            CharacterEquippedWeaponEntity equipWeapon = new CharacterEquippedWeaponEntity();
            equipWeapon.setCharacter(ch);
            equipWeapon.setCharacterWeaponNumber("wpn1");
            characterEquippedWeaponRepository.save(equipWeapon);

            //Saved Character Equipped Skill
            CharacterEquippedSkillsEntity equipSkill = new CharacterEquippedSkillsEntity();
            equipSkill.setCharacter(ch);
            equipSkill.setCharacterSkillNumber("");
            characterEquippedSkillsRepository.save(equipSkill);

            //Saved Character Item Free
            CharacterItemEntity characterItem = new CharacterItemEntity();
            characterItem.setCharacter(ch);
            characterItem.setCharacterItemNumber("1,2,3");
            characterItemRepository.save(characterItem);

            //Saved Character Back Item
            CharacterEquippedBackItemEntity equipBackItem = new CharacterEquippedBackItemEntity();
            equipBackItem.setCharacter(ch);
            equipBackItem.setCharacterEquippedBackItemNumber("");
            characterEquippedBackItemRepository.save(equipBackItem);

            //Saved Character Equipment Accessory
            CharacterEquippedAccessoryEntity equippedAccessory = new CharacterEquippedAccessoryEntity();
            equippedAccessory.setCharacter(ch);
            equippedAccessory.setCharacterEquippedAccessoryNumber("");
            characterEquippedAccessoryRepository.save(equippedAccessory);
            log.info("Character ID {}",ch.getCharacterId());
            result.put("status", "1");
            result.put("character_id", ch.getCharacterId());         // benar: id karakter baru
            result.put("character_name", ch.getCharacterName());
            result.put("account_id", account.getAccountId());
            result.put("message", "Character creation completed successfully");
            return result;
        } catch (Exception e) {
            log.error("[AMF] Create Character error", e);
            result.put("status", "0");
            result.put("error", "Internal error");
            return result;
        } finally {
            log.info("[AMF] End Create Character - Session Key : {}", request.getSessionKey());
        }
    }

    // ==== DELETE (MANUAL LOOP) ====

    // daftar tabel child yang FK -> character_list.character_id
    private static final List<String> CHILD_TABLES = List.of(
            "character_element_point",
            "character_equipped_weapon",
            "character_equipped_body_set",
            "character_equipped_back_item",
            "character_equipped_accessory",
            "character_equipped_skills",
            "bloodline",
            "character_bloodline",
            "character_body_style",
            "character_inv_hair",
            "character_inv_slots",
            "character_item",
            "character_material",
            "character_mission",
            "character_ninja_essence",
            "character_pet_skills",
            "character_pet"
    );

    @Transactional
    public Map<String, Object> deleteCharacter(DeleteCharacterDto.Request request) {
        log.info("[AMF] Start Delete Character - Session Key : {}", request.getSessionKey());
        log.info("[AMF] Start Delete Character - ID Character : {}", request.getCharacterId());

        Map<String, Object> result = new LinkedHashMap<>();
        try {
            var optAcc = accountsRepository.findByAccountSessionKey(request.getSessionKey());
            if (optAcc.isEmpty()) {
                result.put("status", "0");
                result.put("error", "Invalid session key");
                return result;
            }
            AccountsEntity acc = optAcc.get();

            long cid;
            try {
                // sesuaikan kalau request.getCharacterId() tipenya Long langsung
                //2016
                //cid = Long.parseLong(String.valueOf(request.getCharacterId()));
                //RZ
                cid = parseIdLenient(request.getCharacterId());
            } catch (NumberFormatException nfe) {
                result.put("status", "0");
                result.put("error", "Invalid character_id");
                return result;
            }

            // pastikan karakter milik account ini
            boolean owned = characterListRepository.existsById(cid);
            if (!owned) {
                result.put("status", "0");
                result.put("error", "Character not found or not owned");
                return result;
            }

            // 1) Hapus semua tabel anak (loop)
            for (String table : CHILD_TABLES) {
                jdbc.update("DELETE FROM " + table + " WHERE character_id = ?", cid);
            }

            // 2) Hapus parent (sekalian validate account_id)
            int affected = jdbc.update(
                    "DELETE FROM character_list WHERE character_id = ? AND account_id = ?",
                    cid, acc.getAccountId()
            );
            if (affected == 0) {
                result.put("status", "0");
                result.put("error", "Character not found");
                return result;
            }

            result.put("status", "1");
            result.put("result", "Character deleted successfully");
            return result;

        } catch (Exception e) {
            log.error("[AMF] Delete Character error", e);
            result.put("status", "0");
            result.put("error", "Internal error");
            return result;
        } finally {
            log.info("[AMF] End Delete Character - Session Key : {}", request.getSessionKey());
        }
    }

    private static Integer nvl(Integer v, Integer def) {
        return v == null ? def : v;
    }

    private static Long parseIdLenient(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        try {
            return Long.parseLong(s); // "7"
        } catch (NumberFormatException ignore) { /* lanjut */ }

        try {
            double d = Double.parseDouble(s); // "7.0"
            // hanya boleh kalau bilangan bulat (7.0, 10.000, dst)
            if (d % 1 != 0) return null;
            return (long) d;
        } catch (NumberFormatException ignore2) {
            // terakhir: buang trailing .0... kalau ada
            s = s.replaceAll("\\.0+$", "");
            return s.matches("^-?\\d+$") ? Long.parseLong(s) : null;
        }
    }

    private static int toIntSafe(String v, int def) {
        if (v == null) return def;
        v = v.trim();
        if (v.isEmpty()) return def;
        try {
            // coba integer biasa
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            try {
                // kalau datangnya "0.0" / "1.0" dari AMF
                double d = Double.parseDouble(v);
                return (int) d;
            } catch (NumberFormatException ex) {
                return def;
            }
        }
    }
}
