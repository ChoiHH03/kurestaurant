package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Reply;
import miniproject.KUrestaurant.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Long saveReply(Reply reply) {
        if (reply.getStar() > 5 || reply.getStar() < 0) {
            throw new IllegalArgumentException("별점은 0점 이상 5점 미만이어야 합니다");
        }
        replyRepository.save(reply);
        return reply.getId();
    }

    @Transactional
    public void removeReply(Reply reply) {
        Reply.removeReply(reply);
        replyRepository.delete(reply);
    }

    public List<Reply> findReplies() {
        return replyRepository.findAll();
    }

    public Reply findOne(Long replyId) {
        return replyRepository.findById(replyId).get();
    }
}
