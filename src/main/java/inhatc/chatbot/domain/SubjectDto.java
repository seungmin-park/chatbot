package inhatc.chatbot.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubjectDto {

    private String subProf;
    private String subName;
    private String subTitle;
    private String subStart;
    private String subEnd;
}
