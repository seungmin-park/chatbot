package inhatc.chatbot.controller;

import inhatc.chatbot.domain.Subject;
import inhatc.chatbot.domain.SubjectDto;
import inhatc.chatbot.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostConstruct
    public void add() {
        subjectService.join(new Subject("윤경섭", "시스템분석설계", "11주차과제", LocalDateTime.of(2021, 11, 8, 0, 0), LocalDateTime.of(2021, 11, 22, 23, 59)));
        subjectService.join(new Subject("김태간", "오픈소스프로그래밍", "11주차과제", LocalDateTime.of(2021, 11, 8, 0, 0), LocalDateTime.of(2021, 11, 14, 23, 59)));
    }

    @GetMapping("/list")
    public List<Subject> list() {
        return subjectService.findAll();
    }

    @PutMapping("/modify")
    public List<Subject> modify(@RequestBody String name) {
        Subject subject = subjectService.findByName(name);
        subject.modifySubject("교수1","과목명","과제명", LocalDateTime.now(),LocalDateTime.now());
        return subjectService.findAll();
    }

    @PostMapping("/add")
    public List<Subject> add(@RequestBody SubjectDto subjectDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        subjectService.join(new Subject(subjectDto.getSubProf(), subjectDto.getSubName(), subjectDto.getSubTitle(), LocalDateTime.parse(subjectDto.getSubStart(), dateTimeFormatter), LocalDateTime.parse(subjectDto.getSubEnd(), dateTimeFormatter)));
        return subjectService.findAll();
    }

    @DeleteMapping("delete")
    public List<Subject> delete(@RequestParam String name) {
        subjectService.deleteByName(name);
        List<Subject> subjects = subjectService.findAll();
        return subjects;
    }
}
