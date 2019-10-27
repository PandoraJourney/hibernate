package lt.metasite.hibernate;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import lt.metasite.hibernate.model.Message;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Executor {

    private static Logger LOG = LoggerFactory.getLogger(Executor.class);

    @Resource
    private EntityManagerFactory emf;

    @PostConstruct
    public void execute() {
        insertMessage(emf);
        queryMessage(emf);
    }

    @Transactional
    public void insertMessage(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Message message = new Message();
        message.setText("Hello World!");

        em.persist(message);
        tx.commit();
    }
// INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')

    @Transactional
    public void queryMessage(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cr = cb.createQuery(Message.class);
        Root<Message> root = cr.from(Message.class);
        cr.select(root);
        List<Message> messages =
                em.createQuery(cr).getResultList();
// SELECT * from MESSAGE
        if (!messages.isEmpty()) {
            messages.get(0).setText("Take me to your leader!");
            LOG.debug("Message found");
        }
        em.close();
// UPDATE MESSAGE set TEXT = 'Take me to your leader!' where ID = 1
    }
}
