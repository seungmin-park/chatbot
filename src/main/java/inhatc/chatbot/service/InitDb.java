package inhatc.chatbot.service;

import inhatc.chatbot.domain.Subject;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class InitDb {
    private final SubjectService subjectService;

    @PostConstruct
    public void init() {
        subjectService.join(
                new Subject("김태간", "오픈소스프로그래밍", "11주차과제",
                        LocalDateTime.of(2022, 02, 8, 0, 0),
                        LocalDateTime.of(2022, 02, 27, 23, 59))
        );
        subjectService.join(
                new Subject("김태간1", "오픈소스프로그래밍1", "11주차과제1",
                        LocalDateTime.of(2022, 02, 8, 0, 0),
                        LocalDateTime.of(2022, 02, 27, 23, 59))
        );
        subjectService.join(
                new Subject("김태간12", "오픈소스프로그래밍12", "11주차과제12",
                        LocalDateTime.of(2022, 02, 8, 0, 0),
                        LocalDateTime.of(2022, 02, 27, 23, 59))
        );
    }
}
