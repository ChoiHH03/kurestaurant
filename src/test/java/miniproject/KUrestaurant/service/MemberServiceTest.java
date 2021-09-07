package miniproject.KUrestaurant.service;

import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberType;
import miniproject.KUrestaurant.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() {
        Member member = new Member("member", "loginId", "member", MemberType.OWNER);
        memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).get();
        assertEquals(findMember, member);

    }

    @Test
    public void 중복아이디오류() {
        Member member1 = new Member("member", "loginId", "member", MemberType.OWNER);
        Member member2 = new Member("member", "loginId", "member", MemberType.OWNER);
        memberService.join(member1);
        Long duplicatedId = memberService.join(member2);
        assertEquals(duplicatedId, null);
    }

    @Test
    public void 아이디검색() {
        Member member = new Member("member", "loginId", "member", MemberType.OWNER);
        memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByLoginId("loginId2");
        System.out.println(findMember.isEmpty());
    }
}