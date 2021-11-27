package inhatc.chatbot.service;

import inhatc.chatbot.domain.Subject;
import inhatc.chatbot.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private int validateDuplicateSubject(Subject subject) {
        List<Subject> subjects = subjectRepository.findAll();
        for (Subject subject1 : subjects) {
            if (subject.getSubTitle().equals(subject1.getSubTitle()) && subject.getSubProf().equals(subject1.getSubProf())) {
                return -1;
            }
        }
        return 0;
    }

    @Transactional
    public Long join(Subject subject) {
        int validation = validateDuplicateSubject(subject);
        if (validation == -1) {
            return -1L;
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findByName(String name) {
        return subjectRepository.findByName(name);
    }

    @Transactional
    public Long updateSubject(String name,String subProf, String subName, String subTitle, LocalDateTime subStart, LocalDateTime subEnd) {
        Subject subject = findByName(name);
        subject.modifySubject(subProf, subName, subTitle, subStart, subEnd);
        Long modifiedSubjectId = join(subject);
        return modifiedSubjectId;
    }

    @Transactional
    public Long deleteByName(String name) {
        return subjectRepository.deleteByName(name);
    }

    @Transactional
    public void clear() {
        subjectRepository.clear();
    }

    public Subject findById(Long id) {
        return subjectRepository.findById(id);
    }
}
