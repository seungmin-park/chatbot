package inhatc.chatbot.domain.response;

import lombok.Data;

@Data
public class UserSubjectResponse {

    private String version = "2.0";
    private Template template;
}
