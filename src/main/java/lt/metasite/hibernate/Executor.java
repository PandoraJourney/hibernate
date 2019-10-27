package lt.metasite.hibernate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import lt.metasite.hibernate.model.Message;

import org.springframework.stereotype.Component;

@Component
public class Executor {

    @Resource
    private EntityManagerFactory emf;

    @PostConstruct
    @Transactional
    public void execute() {

        EntityManager em = emf.createEntityManager();
        Message message = new Message();
        message.setText("Hello World!");
        em.persist(message);
// INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')
        em.close();
    }
}
