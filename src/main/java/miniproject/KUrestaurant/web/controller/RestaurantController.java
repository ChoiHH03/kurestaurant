package miniproject.KUrestaurant.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import miniproject.KUrestaurant.domain.*;
import miniproject.KUrestaurant.file.FileStore;
import miniproject.KUrestaurant.service.MemberRestaurantService;
import miniproject.KUrestaurant.service.MemberService;
import miniproject.KUrestaurant.service.RestaurantService;
import miniproject.KUrestaurant.web.form.RestaurantForm;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MemberService memberService;
    private final MemberRestaurantService memberRestaurantService;
    private final FileStore fileStore;

    @ModelAttribute("categories")
    public Category[] categories() {
        return Category.values();
    }

    @GetMapping
    public String Restaurants(@SessionAttribute(name = "loginMember") Member loginMember,
                              @RequestParam(required = false) String name,
                              @RequestParam(name = "category", defaultValue = "none") String categoryName,
                              @PageableDefault(size = 5) Pageable pageable,
                              @ModelAttribute("condition") RestaurantSearchCond condition, Model model) {


        Category category = checkCategory(categoryName);
        RestaurantSearchCond cond = new RestaurantSearchCond();
        cond.setName(name);
        cond.setCategory(category);

        Page<Restaurant> result = restaurantService.findRestaurantsCond(cond, pageable);
        List<Restaurant> restaurants = result.getContent();
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("page", result);
        model.addAttribute("member", loginMember);
        return "restaurants/restaurants";
    }

    @GetMapping("/{restaurantId}")
    public String Restaurant(@PathVariable long restaurantId,
                             @SessionAttribute(name = "loginMember") Member loginMember,
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
        model.addAttribute("form", new RestaurantForm());
        return "restaurants/addForm";
    }

    @PostMapping("/add")
    public String addRestaurant(@Validated @ModelAttribute("form") RestaurantForm form, BindingResult bindingResult,
                                @SessionAttribute(name = "loginMember") Member loginMember,
                                RedirectAttributes redirectAttributes) throws IOException {

        if (!form.getPhoneNumber().matches("\\d{2,3}-\\d{3,4}-\\d{3,4}")) {
            bindingResult.rejectValue("phoneNumber","type",null);
            return "restaurants/addForm";
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "restaurants/addForm";
        }

        String fileName = fileStore.storeFile(form.getAttachFile());

        Restaurant restaurant = new Restaurant(form.getName(), form.getPhoneNumber(), form.getAddress(), form.getCategory(), form.isDelivery(), loginMember, fileName);

        Long savedRestaurantId = restaurantService.saveRestaurant(restaurant);

        redirectAttributes.addAttribute("restaurantId", savedRestaurantId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/restaurants/{restaurantId}";
    }

    @GetMapping("/pick")
    public String pickList(@SessionAttribute(name = "loginMember") Member loginMember, Model model){
        Member member = memberService.findOne(loginMember.getId());
        List<Restaurant> restaurants = memberRestaurantService.findByMember(member);
        model.addAttribute("restaurants", restaurants);
        return "restaurants/pickRestaurants";
    }

    @GetMapping("/{restaurantId}/delete")
    public String deleteRestaurant(@PathVariable Long restaurantId,
                                   @SessionAttribute(name = "loginMember") Member loginMember) {
        Restaurant restaurant = restaurantService.findOne(restaurantId);
        if (restaurant.getMember().getId() != loginMember.getId()) {
            return "redirect:/restaurants/{restaurantId}";
        }
        restaurantService.removeRestaurant(restaurantId);
        return "redirect:/restaurants";
    }

    @GetMapping("/{restaurantId}/edit")
    public String editForm(@PathVariable Long restaurantId,
                           @SessionAttribute(name = "loginMember") Member loginMember,
                           Model model) {
        Restaurant restaurant = restaurantService.findOne(restaurantId);

        if (restaurant.getMember().getId() != loginMember.getId()) {
            return "redirect:/restaurants/{restaurantId}";
        }

        RestaurantForm form = new RestaurantForm();
        form.setName(restaurant.getName());
        form.setPhoneNumber(restaurant.getPhoneNumber());
        form.setAddress(restaurant.getAddress());
        form.setCategory(restaurant.getCategory());
        form.setDelivery(restaurant.isDelivery());

        model.addAttribute("form", form);

        return "restaurants/editForm";
    }

    @PostMapping("/{restaurantId}/edit")
    public String editRestaurant(@Validated @ModelAttribute("form") RestaurantForm form, BindingResult bindingResult,
                                 @PathVariable Long restaurantId,
                                 @SessionAttribute(name = "loginMember") Member loginMember
                                 ) throws IOException {

        Restaurant restaurant = restaurantService.findOne(restaurantId);

        if (restaurant.getMember().getId() != loginMember.getId()) {
            return "redirect:/restaurants/{restaurantId}";
        }

        if (!form.getPhoneNumber().matches("\\d{2,3}-\\d{3,4}-\\d{3,4}")) {
            bindingResult.rejectValue("phoneNumber","type",null);
            return "restaurants/editForm";
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "restaurants/editForm";
        }

        String fileName = fileStore.storeFile(form.getAttachFile());

        if (fileName != null && restaurant.getImage() != null) {
            fileStore.deleteFile(restaurant.getImage());
            log.info("delete image");
        }

        restaurantService.editRestaurant(restaurantId, form.getName(), form.getPhoneNumber(), form.getAddress(), form.isDelivery(), fileName);

        return "redirect:/restaurants/{restaurantId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        log.info("fileName={}", filename);
        log.info("fileRoute={}", fileStore.getFullPath(filename));
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    private Category checkCategory(String categoryName) {
        for (Category category : Category.values()) {
            if (categoryName.equals(category.toString())) {
                return category;
            }
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/api")
    public Page<Restaurant> searchMemberV3(RestaurantSearchCond condition, Pageable pageable) {
        return restaurantService.findRestaurantsCond(condition, pageable);
    }
}
