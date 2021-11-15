package inhatc.chatbot.repository;

import inhatc.chatbot.domain.Subject;
import inhatc.chatbot.service.SubjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectService subjectService;

    @Test
    @DisplayName("과제등록")
    @Transactional
    void 과제등록() throws Exception {
        //given
        Subject subject = new Subject("윤경섭", "시스템분석설계", "11주차과제", LocalDateTime.of(2021, 11, 8, 0, 0), LocalDateTime.of(2021, 11, 22, 23, 59));
        //when
        Long subId = subjectService.join(subject);
        Subject subFindByTitle = subjectService.findByName("시스템분석설계");
        //then
        assertThat(subId).isEqualTo(subFindByTitle.getId());
        assertThat(subFindByTitle.getSubProf()).isEqualTo("윤경섭");
        assertThat(subFindByTitle.getSubName()).isEqualTo("시스템분석설계");
        assertThat(subFindByTitle.getRemainDay()).isEqualTo(13);
    }

    @Test
    @DisplayName("과제조회")
    @Transactional
    void 과제조회() throws Exception {
        //given
        Subject subject1 = new Subject("윤경섭", "시스템분석설계", "11주차과제", LocalDateTime.of(2021, 11, 8, 0, 0), LocalDateTime.of(2021, 11, 22, 23, 59));
        Subject subject2 = new Subject("김태간", "오픈소스프로그래밍", "10주차과제", LocalDateTime.of(2021, 11, 8, 0, 0), LocalDateTime.of(2021, 11, 14, 23, 59));
        subjectService.join(subject1);
        subjectService.join(subject2);
        //when
        List<Subject> subjects = subjectService.findAll();
        //then
        assertThat(subjects.size()).isEqualTo(2);
        assertThat(subjects.get(0).getSubProf()).isEqualTo("윤경섭");
        assertThat(subjects.get(0).getSubName()).isEqualTo("시스템분석설계");
        assertThat(subjects.get(0).getSubTitle()).isEqualTo("11주차과제");
        assertThat(subjects.get(0).getSubStart().toString()).isEqualTo("2021-11-08T00:00");
        assertThat(subjects.get(0).getSubEnd().toString()).isEqualTo("2021-11-22T23:59");
        assertThat(subjects.get(0).getRemainDay()).isEqualTo(13);
        assertThat(subjects.get(1).getSubProf()).isEqualTo("김태간");
        assertThat(subjects.get(1).getSubName()).isEqualTo("오픈소스프로그래밍");
        assertThat(subjects.get(1).getSubTitle()).isEqualTo("10주차과제");
        assertThat(subjects.get(1).getSubStart().toString()).isEqualTo("2021-11-08T00:00");
        assertThat(subjects.get(1).getSubEnd().toString()).isEqualTo("2021-11-14T23:59");
        assertThat(subjects.get(1).getRemainDay()).isEqualTo(5);
    }

    @Test
    @DisplayName("과제수정")
    @Transactional
    void 과제수정() throws Exception {
        //given
        Subject subject = new Subject("윤경서", "시스템분석설", "11주차과", LocalDateTime.of(2021, 11, 9, 0, 0), LocalDateTime.of(2021, 11, 23, 23, 59));
        subjectService.join(subject);
        Subject subject1 = subjectService.findByName("시스템분석설");
        //when
        subjectService.updateSubject("시스템분석설", "윤경섭", "시스템분석설계", "11주차과제",LocalDateTime.of(2021, 11, 8, 0, 0), LocalDateTime.of(2021, 11, 22, 23, 59));

        //then
        assertThat(subject1.getSubProf()).isEqualTo("윤경섭");
    }
}