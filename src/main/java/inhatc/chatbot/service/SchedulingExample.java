package inhatc.chatbot.service;

import inhatc.chatbot.domain.SeleniumCrawling;
import inhatc.chatbot.domain.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingExample {

    private final SeleniumCrawling seleniumCrawling;
    private final SubjectService subjectService;

    @Scheduled(cron = "30 * * * * *")
    public void testMethod() {
        seleniumCrawling.subjectCrawling();
        List<Subject> subjects = subjectService.findAll();
        for (Subject subject : subjects) {
            if (subject.getRemainDay() < 0) {
                subjectService.deleteByName(subject.getSubName());
            }
        }
        for (Subject subject : subjects) {
            log.info("id = {}",subject.getId());
            log.info("pof = {}",subject.getSubProf());
            log.info("subName = {}",subject.getSubName());
            log.info("Title = {}",subject.getSubTitle());
            log.info("Start = {}",subject.getSubStart());
            log.info("End = {}",subject.getSubEnd());
            log.info("Remain = {}",subject.getRemainDay());
        }
    }
}
