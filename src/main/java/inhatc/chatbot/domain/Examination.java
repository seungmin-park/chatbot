package inhatc.chatbot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subName;
    private String subProf;

    private String method;

    private LocalDateTime subDate;
    private String subLoc;

    public Examination(String subName, String subProf, String method, LocalDateTime subDate, String subLoc) {
        this.subName = subName;
        this.subProf = subProf;
        this.method = method;
        this.subDate = subDate;
        this.subLoc = subLoc;
    }

    @Override
    public String toString() {
        return "교수명 : " + subProf +
                "\n시험 방식 : " + method +
                "\n시험 날짜 : " + subDate +
                "\n시험 장소 : " + subLoc;
    }
}
