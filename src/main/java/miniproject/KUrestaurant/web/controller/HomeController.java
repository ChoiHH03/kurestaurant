package miniproject.KUrestaurant.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.service.MemberService;
import miniproject.KUrestaurant.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

/*    @GetMapping("/")
    public String home() {
        return "home";
    }*/

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                    Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        return "redirect:/restaurants";
    }
}
