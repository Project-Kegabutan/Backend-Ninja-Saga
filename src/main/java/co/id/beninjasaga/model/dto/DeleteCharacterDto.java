package co.id.beninjasaga.model.dto;

import lombok.Data;

import java.util.List;

public class DeleteCharacterDto {
    @Data
    public static class Request {
        private String sessionKey;
        private String characterId;
    }

    @Data
    public static class Response{
        private String status;
        List<List<Object>> result;
        private String login_per_day;
    }
}
