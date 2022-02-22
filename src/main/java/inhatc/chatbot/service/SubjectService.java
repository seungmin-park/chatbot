package inhatc.chatbot.service;

import inhatc.chatbot.domain.Subject;
import inhatc.chatbot.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Transactional
    public void join(Subject subject) {
        List<Subject> subjects = subjectRepository.findAll();
        for (Subject s : subjects) {
            if (!s.getSubProf().equals(subject.getSubProf()) || !s.getSubName().equals(subject.getSubName())) {
                subjectRepository.save(subject);
                break;
            }
        }
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findBySubName(String subName) {
        return subjectRepository.findBySubName(subName);
    }

    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateRemainDay() {
        List<Subject> subjects = findAll();
        subjects.forEach(Subject::updateRemainDate);
    }

    @Transactional
    @Scheduled(cron = "0 3 0 * * *")
    public void deleteTimeOutSubject() {
        List<Subject> subjects = findAll();
        subjects.forEach(s -> {
            if (s.getRemainDay() < 0) {
                subjectRepository.delete(s);
            }});
    }
}
