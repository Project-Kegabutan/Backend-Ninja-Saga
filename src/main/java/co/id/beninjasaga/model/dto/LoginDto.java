package co.id.beninjasaga.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class LoginDto {

    @Data
    public static class Request {
        private String username;
        private String password;
        private String buildNo;
        private String buildReview;
    }
}
