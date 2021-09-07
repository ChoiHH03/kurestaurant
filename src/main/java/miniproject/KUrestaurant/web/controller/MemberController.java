package miniproject.KUrestaurant.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberType;
import miniproject.KUrestaurant.service.MemberService;
import miniproject.KUrestaurant.web.form.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() {
        return MemberType.values();
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("form") MemberForm form) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("form") MemberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

        Long memberId = memberService.findByName(form.getName());

        if (memberId != null) {
            bindingResult.rejectValue("name", "duplicate",null);
        }

        Member member = new Member(form.getName(), form.getLoginId(), form.getPassword(), form.getMemberType());

        Long joinId = memberService.join(member);

        if (joinId == null) {
            bindingResult.rejectValue("loginId", "duplicate",null);
        }

        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        return "redirect:/";
    }
}
