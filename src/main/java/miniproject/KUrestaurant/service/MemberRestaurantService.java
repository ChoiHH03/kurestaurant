package miniproject.KUrestaurant.service;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberRestaurant;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.repository.MemberRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRestaurantService {

    private final MemberRestaurantRepository memberRestaurantRepository;

    @Transactional
    public Long pickRestaurant(MemberRestaurant memberRestaurant) {
        memberRestaurantRepository.save(memberRestaurant);

        return memberRestaurant.getId();
    }

    @Transactional
    public void unpickRestaurant(Member member, Restaurant restaurant) {
        MemberRestaurant memberRestaurant = memberRestaurantRepository.findByMemberAndRestaurant(member, restaurant).get();
        MemberRestaurant.removeMemberRestaurant(memberRestaurant);
        memberRestaurantRepository.delete(memberRestaurant);
    }

    public boolean isPicked(Member member, Restaurant restaurant) {
        Optional<MemberRestaurant> memberRestaurant = memberRestaurantRepository.findByMemberAndRestaurant(member, restaurant);

        if (memberRestaurant.isPresent()) {
            return true;
        }
        return false;
    }

    public MemberRestaurant findOne(Member member, Restaurant restaurant) {
        return memberRestaurantRepository.findByMemberAndRestaurant(member, restaurant).get();
    }

    public List<Restaurant> findByMember(Member member) {
        return memberRestaurantRepository.findByMember(member);
    }

    public MemberRestaurant findById(Long memberRestaurantId) {
        return memberRestaurantRepository.findById(memberRestaurantId).get();
    }
}
