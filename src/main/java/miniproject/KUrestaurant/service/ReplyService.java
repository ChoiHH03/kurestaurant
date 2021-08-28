package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Reply;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Reply createReply(Member member, Restaurant restaurant, int star, String comment, LocalDate date) {
        Reply reply = Reply.createReply(member, restaurant, star, comment, date);
        return reply;
    }

    @Transactional
    public Long saveReply(Reply reply) {
        replyRepository.save(reply);
        return reply.getId();
    }

    @Transactional
    public void removeReply(Reply reply) {
        Reply.removeReply(reply);
        replyRepository.remove(reply);
    }

    public List<Reply> findReplies() {
        return replyRepository.findAll();
    }

    public Reply findOne(Long replyId) {
        return replyRepository.findOne(replyId);
    }
}
