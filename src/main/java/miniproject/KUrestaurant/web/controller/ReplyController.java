package miniproject.KUrestaurant.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Reply;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.StarCode;
import miniproject.KUrestaurant.service.MemberService;
import miniproject.KUrestaurant.service.ReplyService;
import miniproject.KUrestaurant.service.RestaurantService;
import miniproject.KUrestaurant.web.form.ReplyForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class ReplyController {

    private final ReplyService replyService;
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    @ModelAttribute("starCode")
    public List<StarCode> star() {
        List<StarCode> starCode = new ArrayList<>();
        starCode.add(new StarCode(1, "★"));
        starCode.add(new StarCode(2, "★★"));
        starCode.add(new StarCode(3, "★★★"));
        starCode.add(new StarCode(4, "★★★★"));
        starCode.add(new StarCode(5, "★★★★★"));
        return starCode;
    }

    @GetMapping("/{restaurantId}/reply")
    public String addForm(@PathVariable long restaurantId,
                          @ModelAttribute("form") ReplyForm form) {

        return "replies/addForm";
    }

    @PostMapping("/{restaurantId}/reply")
    public String addReply(@PathVariable long restaurantId,
                           @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                           @Validated @ModelAttribute ReplyForm form,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "replies/addForm";
        }

        Restaurant restaurant = restaurantService.findOne(restaurantId);
        Member member = memberService.findOne(loginMember.getId());
        Reply reply = replyService.createReply(member, restaurant, form.getStar(), form.getComment(), LocalDate.now());
        replyService.saveReply(reply);

        redirectAttributes.addAttribute("restaurantId", restaurantId);
        return "redirect:/restaurants/{restaurantId}";
    }
}
