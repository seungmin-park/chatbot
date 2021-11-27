package inhatc.chatbot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * subProf : 교수명
     * subName : 과목이름
     * subTitle : 과제 명
     * subStart : 시작 일
     * subEnd : 종료 일
     * remainDay : 남은 기간
     */
    private String subProf;
    private String subName;
    private String subTitle;
    private LocalDateTime subStart;
    private LocalDateTime subEnd;
    private Long remainDay;

    public void modifySubject(String subProf, String subName, String subTitle, LocalDateTime subStart, LocalDateTime subEnd) {
        this.subProf = subProf;
        this.subName = subName;
        this.subTitle = subTitle;
        this.subStart = subStart;
        this.subEnd = subEnd;
        this.remainDay = ChronoUnit.DAYS.between(subStart, subEnd);
    }

    public Subject(String subProf, String subName, String subTitle, LocalDateTime subStart, LocalDateTime subEnd) {
        this.subProf = subProf;
        this.subName = subName;
        this.subTitle = subTitle;
        this.subStart = subStart;
        this.subEnd = subEnd;
        this.remainDay = ChronoUnit.DAYS.between(LocalDateTime.now(), subEnd);
    }

    @Override
    public String toString() {
        return
                "교수명 : " + subProf +
                "\n과목명 : " + subName +
                "\n과제명 : " + subTitle +
                "\n과제 시작일 : " + subStart +
                "\n과제 마감일 : " + subEnd +
                "\n남은 기간 : " + remainDay +"일";
    }
}
