package miniproject.KUrestaurant.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.KUrestaurant.domain.Category;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.service.MemberRestaurantService;
import miniproject.KUrestaurant.service.MemberService;
import miniproject.KUrestaurant.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MemberService memberService;
    private final MemberRestaurantService memberRestaurantService;

    @ModelAttribute("categories")
    public Category[] categories() {
        return Category.values();
    }

    @GetMapping
    public String Restaurants(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {
        List<Restaurant> restaurants = restaurantService.findRestaurants();
        model.addAttribute("restaurants", restaurants);

        model.addAttribute("member", loginMember);
        return "restaurants/restaurants";
    }

    @GetMapping("/{restaurantId}")
    public String Restaurant(@PathVariable long restaurantId,
                             @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                             Model model) {
        Restaurant restaurant = restaurantService.findOne(restaurantId);
        Member member = memberService.findOne(loginMember.getId());
        boolean status = memberRestaurantService.isPicked(member, restaurant);
        model.addAttribute("member", member);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("pick_status", status);
        return "restaurants/restaurant";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurants/addForm";
    }

    @PostMapping("/add")
    public String addRestaurant(@Validated @ModelAttribute("restaurant") Restaurant restaurant, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!restaurant.getPhoneNumber().chars().allMatch(Character::isDigit)) {
            bindingResult.rejectValue("phoneNumber","type",null);
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "restaurants/addForm";
        }

        Long savedRestaurantId = restaurantService.saveRestaurant(restaurant);

        redirectAttributes.addAttribute("restaurantId", savedRestaurantId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/restaurants/{restaurantId}";
    }

    @GetMapping("/pick")
    public String pickList(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model){
        Member member = memberService.findOne(loginMember.getId());
        List<Restaurant> restaurants = memberRestaurantService.findByMember(member);
        model.addAttribute("restaurants", restaurants);
        return "restaurants/pickRestaurants";
    }
}
