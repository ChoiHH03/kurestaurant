package miniproject.KUrestaurant.repository;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Reply;
import miniproject.KUrestaurant.domain.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {

    private final EntityManager em;

    public void save(Reply reply) {
        em.persist(reply);
    }

    public void remove(Reply reply) {
        em.remove(reply);
    }

    public Reply findOne(Long replyId) {
        return em.find(Reply.class, replyId);
    }

    public List<Reply> findAll() {
        return em.createQuery("select r from Reply r", Reply.class)
                .getResultList();
    }

    public List<Reply> findRestaurantReply(Restaurant restaurant) {
        return em.createQuery("select r from Reply r where r.restaurant = :restaurant", Reply.class)
                .setParameter("restaurant", restaurant)
                .getResultList();
    }

}
