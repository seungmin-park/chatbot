package inhatc.chatbot.service;

import inhatc.chatbot.domain.Examination;
import inhatc.chatbot.repository.ExaminationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    @Transactional
    public Object join(Examination examination) {
        return examinationRepository.save(examination);
    }

    public List<Examination> findAll() {
        return examinationRepository.findAll();
    }

}
