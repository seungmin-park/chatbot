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

    @Transactional
    public Long join(Subject subject) {
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
}