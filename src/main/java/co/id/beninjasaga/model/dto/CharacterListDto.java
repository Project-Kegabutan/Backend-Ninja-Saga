package co.id.beninjasaga.model.dto;

import lombok.Data;

import java.util.List;

public class CharacterListDto {

    @Data
    public static class Request {
        private String sessionKey;
    }

    @Data
    public static class Response{
        private String status;
        List<List<Object>> result;
        private String login_per_day;
    }
}
