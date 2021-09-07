package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
/*        Optional<Member> loginMember = memberRepository.findByLoginId(member.getLoginId());
        if (loginMember.isPresent()) {
            return null;
        }*/
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public Long findByName(String name) {
        List<Member> member = memberRepository.findByName(name);
        return (!member.isEmpty()) ? member.get(0).getId() : null;
    }

    public Long findByLoginId(String loginId) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        return (member.isPresent()) ? member.get().getId() :  null;

    }
}