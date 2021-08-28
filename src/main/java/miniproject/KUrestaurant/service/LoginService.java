package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
