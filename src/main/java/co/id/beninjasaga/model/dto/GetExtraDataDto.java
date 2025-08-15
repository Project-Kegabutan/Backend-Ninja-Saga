package co.id.beninjasaga.model.dto;

import lombok.*;

import java.util.List;

public class GetExtraDataDto {
    @Data
    public static class Request {
        public String sessionKey;
        public String hashXP;
    }

    @Builder
    @Data
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @com.fasterxml.jackson.annotation.JsonInclude(
            com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS // tampilkan null
    )
    public static class Response{
        public Integer status;
        public result result;
    }

    public static class result{
        public Integer characterId;
        public String characterName;
        public Integer characterLevel;
        public Integer characterHair;
        List<List<Object>> characterSkills;
        List<List<Object>> characterBodyParts;
        public String characterEquippedWeapon;
        public Integer charLoginPerDay;
        public pvpRecord pvpRecord;
        public String extraDataHash;
        List<List<Object>> pvpSchedule;
        public Integer seasonNumber;
        List<List<trainingSkill>> training_skill;
        public Integer petId;
        List<List<Object>> playerPet;
        List<List<Object>> bpMissionId;
        public boolean newMail;
        boolean getHuntingPassport;
        public Integer clanId;
        public Integer seDayCountOpen;
        public Integer seEndDate;
        public Integer seEndDateNotice;
        public boolean pvpInvite;
        public Integer sjeEndDate;
        public Integer sjeEndDateNotice;
        public List<List<Object>> newsArr;
        public List<List<Object>> newsId;
        public boolean isGraphic;
        public Integer prestige;
        public List<List<Object>> senjutsuSystem;
        public List<List<Object>> bloodline;
        public List<List<Object>> senjutsu;
    }
    public static class pvpRecord{
        public Integer play;
        public Integer win;
        public Integer lose;
        public Integer disconnect;
        public Integer avgLevelDiff;
        public Integer pvpCurrency;
        public Integer pvpPoint;
        public Integer pvpTournamentTicket;
    }

    public static class trainingSkill{
        public Integer id;
        public Integer time;
    }
}
