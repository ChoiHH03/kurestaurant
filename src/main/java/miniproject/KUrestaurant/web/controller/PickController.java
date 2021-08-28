package miniproject.KUrestaurant.web.controller;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.service.MemberRestaurantService;
import miniproject.KUrestaurant.service.MemberService;
import miniproject.KUrestaurant.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class PickController {

    private final MemberRestaurantService memberRestaurantService;
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    @PostMapping("/{restaurantId}/pick")
    public String pick(@PathVariable long restaurantId,
                       @SessionAttribute(name = "loginMember", required = false) Member loginMember
                                ) {
        Member member = memberService.findOne(loginMember.getId());
        Restaurant restaurant = restaurantService.findOne(restaurantId);
        memberRestaurantService.pickRestaurant(member, restaurant);
        return "redirect:/restaurants/{restaurantId}";
    }

    @PostMapping("/{restaurantId}/unpick")
    public String unpick(@PathVariable long restaurantId,
                         @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                         @RequestParam String redirectURI
    ) {
        Member member = memberService.findOne(loginMember.getId());
        Restaurant restaurant = restaurantService.findOne(restaurantId);
        memberRestaurantService.unpickRestaurant(member, restaurant);
        return "redirect:" + redirectURI;
    }
}
