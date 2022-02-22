package inhatc.chatbot.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSubjectResponse {

    private final String version = "2.0";
    private Template template;
}
