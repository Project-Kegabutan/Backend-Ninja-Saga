package co.id.beninjasaga.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class GetExtraDataDto {
    @Data
    public static class Request {
        public String sessionKey;
        public String hashXP;
    }


    @com.fasterxml.jackson.annotation.JsonInclude(
            com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS // tampilkan null
    )
    @Builder
    @Data
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        public Integer status;
        public result result;
    }
    @Builder
    @Data
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class result{
        @JsonProperty("character_id")
        public Integer characterId;
        @JsonProperty("character_name")
        public String characterName;
        @JsonProperty("character_level")
        public Integer characterLevel;
        @JsonProperty("character_hair")
        public Object characterHair;
        @JsonProperty("character_skills")
        public List<List<Object>> characterSkills;
        @JsonProperty("character_body_parts")
        public List<List<Object>> characterBodyParts;
        @JsonProperty("character_equipped_weapon")
        public String characterEquippedWeapon;
        @JsonProperty("char_login_per_day")
        public Integer charLoginPerDay;
        @JsonProperty("pvp_record")
        public pvpRecord pvpRecord;
        @JsonProperty("extra_data_hash")
        public String extraDataHash;
        @JsonProperty("pvpSchedule")
        public List<List<Object>> pvpSchedule;
        @JsonProperty("seasonNumber")
        public Integer seasonNumber;
        @JsonProperty("training_skill")
        public trainingSkill training_skill;
        @JsonProperty("petid")
        public Integer petId;
        @JsonProperty("player_pet")
        public List<List<Object>> playerPet;
        @JsonProperty("bp_mission_id")
        public List<List<Object>> bpMissionId;
        @JsonProperty("new_mail")
        public boolean newMail;
        @JsonProperty("character_create_date")
        public String characterCreateDate;
        @JsonProperty("get_hunting_passport")
        public boolean getHuntingPassport;
        @JsonProperty("clan_id")
        public Integer clanId;
        @JsonProperty("se_day_count_open")
        public Integer seDayCountOpen;
        @JsonProperty("se_end_date")
        public Integer seEndDate;
        @JsonProperty("se_end_date_notice")
        public Integer seEndDateNotice;
        @JsonProperty("pvp_invite")
        public boolean pvpInvite;
        @JsonProperty("sje_end_date")
        public Integer sjeEndDate;
        @JsonProperty("sje_end_date_notice")
        public Integer sjeEndDateNotice;
        @JsonProperty("newsArr")
        public List<List<Object>> newsArr;
        @JsonProperty("newsId")
        public List<List<Object>> newsId;
        @JsonProperty("isGraphic")
        public boolean isGraphic;
        @JsonProperty("prestige")
        public Integer prestige;
        @JsonProperty("senjutsu_system")
        public List<List<Object>> senjutsuSystem;
        @JsonProperty("bloodline")
        public List<List<Object>> bloodline;
        @JsonProperty("senjutsu")
        public List<List<Object>> senjutsu;
    }
    @Builder
    @Data
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class pvpRecord{
        @JsonProperty("play")
        public Integer play;
        @JsonProperty("win")
        public Integer win;
        @JsonProperty("lose")
        public Integer lose;
        @JsonProperty("disconnect")
        public Integer disconnect;
        @JsonProperty("avg_level_diff")
        public Integer avgLevelDiff;
        @JsonProperty("pvp_currency")
        public Integer pvpCurrency;
        @JsonProperty("pvp_point")
        public Integer pvpPoint;
        @JsonProperty("pvp_tournament_ticket")
        public Integer pvpTournamentTicket;
    }
    @Builder
    @Data
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class trainingSkill{
        public Integer id;
        public Integer time;
    }
}
