package co.id.beninjasaga.model.dto;
import lombok.Data;

@Data
public class SelectFreeSkillDto {
    @Data
    public static class Request {
        public String sessionKey;
        public String skillNumber;
    }
}
