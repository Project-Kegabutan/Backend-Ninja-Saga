package co.id.beninjasaga.model.dto;

import lombok.Data;

public class GetCreateCharacterDto {
    @Data
    public static class Request {
        private String sessionKey;
        private String testVersion;
    }
}
