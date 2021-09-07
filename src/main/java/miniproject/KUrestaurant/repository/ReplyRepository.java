package miniproject.KUrestaurant.repository;

import miniproject.KUrestaurant.domain.Reply;
import miniproject.KUrestaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
