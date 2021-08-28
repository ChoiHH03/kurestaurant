package miniproject.KUrestaurant.service;

import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Reply;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyServiceTest {

    @Autowired private ReplyService replyService;

    @Test
    public void 댓글등록() {

        Member member = new Member();
        Restaurant restaurant = new Restaurant();

        Reply reply = replyService.createReply(member, restaurant,4,"맛있어요", LocalDate.now());
        Long id = replyService.saveReply(reply);

        assertEquals(member.getReplies().size(),1);
        assertEquals(restaurant.getReplies().size(),1);
        assertEquals(replyService.findOne(id),reply);
        assertEquals(restaurant.getEval_num(),1);
    }

    @Test
    public void 별점오류() {

        Member member = new Member();
        Restaurant restaurant = new Restaurant();

        Reply reply = replyService.createReply(member, restaurant,6,"별점오류", LocalDate.now());

        assertThrows(IllegalArgumentException.class, () ->
                replyService.saveReply(reply));
    }

    @Test
    public void 댓글삭제() {
        Member member = new Member();
        Restaurant restaurant = new Restaurant();

        Reply reply = replyService.createReply(member, restaurant,4,"맛있어요", LocalDate.now());

        replyService.saveReply(reply);
        replyService.removeReply(reply);

        assertEquals(replyService.findReplies().size(),0);
        assertEquals(member.getReplies().size(), 0);
        assertEquals(restaurant.getEval_num(), 0);

    }
}