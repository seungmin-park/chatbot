package inhatc.chatbot.repository;

import inhatc.chatbot.domain.Examination;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    @Override
    List<Examination> findAll(Sort sort);
}
