package co.id.beninjasaga.model.dto;

import lombok.Data;

@Data
public class ValidateSkillCharacter {
    @Data
    public static class Request {
        public String sessionKey;
        public String hash;
        public String skillNo;

    }
}
