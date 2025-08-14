package co.id.beninjasaga.model.dto;

import lombok.Data;


public class LoginDto {

    @Data
    public static class Request {
        private String username;
        private String password;
        private String buildNo;
        private String buildReview;
    }
}
