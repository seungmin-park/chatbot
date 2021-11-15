package inhatc.chatbot.repository;

import inhatc.chatbot.domain.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubjectRepository {

    private final EntityManager em;

    public Long save(Subject subject) {
        // insert
        em.persist(subject);
        return subject.getId();
    }

    public List<Subject> findAll() {
        //select * from subject;
        return em.createQuery("select s from Subject s", Subject.class).getResultList();
    }

    public Subject findByName(String name) {
        //select * from subject where sub_name = name;
        return em.createQuery("select s from Subject s where s.subName =: name", Subject.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Long deleteByName(String name) {
        //delete from subject where sub_name = name;
        Long removeItemId = findByName(name).getId();
        em.remove(findByName(name));
        return removeItemId;
    }

    public void clear() {
        em.clear();
    }
}
