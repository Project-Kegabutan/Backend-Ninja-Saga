package co.id.beninjasaga.model.dto;

import lombok.Data;

import java.util.List;

public class CreateCharacterDto {

    @Data
    public static class Request {
        private String sessionKey;
        private String characterName;
        private String characterGender;
        // dari client: "01_0" dll
        private String characterHairColor;
        private String characterSkinColor;

        private String characterHair;
        private String characterFace;
    }

    @Data
    public static class Response{
        private String status;
        private Long characterId;
        private String characterName;
        private Long accountId;
        List<List<Object>> result;
        private String message;
    }
}
