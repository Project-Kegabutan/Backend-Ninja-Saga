package co.id.beninjasaga.model.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.validation.constraints.Email;

@Data
@Builder
public class AuthDto {

        @Data
        public static class RequestRegistration {
            private String username;
            private String password;

            //@Email
            private String email;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonPropertyOrder({"isSuccess","payload","responseCode","responseMessage"})
        public static class ResponseRegistration{
            @JsonProperty("isSuccess")
            boolean isSuccess;
            private String responseCode;
            private String responseMessage;
            private Object payload;
        }
}
