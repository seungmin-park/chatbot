package inhatc.chatbot.repository;

import inhatc.chatbot.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findBySubName(String subName);
}
