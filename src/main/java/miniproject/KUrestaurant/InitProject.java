package miniproject.KUrestaurant;

import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Category;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberType;
import miniproject.KUrestaurant.domain.Restaurant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class InitProject {

    private final InitProjectService initProjectService;

    @PostConstruct
    public void init() {
        initProjectService.init();
    }

    @Component
    static class InitProjectService {

        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            Member member = new Member("member", "member", "member", MemberType.OWNER);
            em.persist(member);

            for (int i = 0; i < 31; i++) {
                Restaurant chinese = new Restaurant("식당" + i, "00-000-000", "서울", Category.CHINESE, false, member, null);
                Restaurant italian = new Restaurant("식당" + i, "00-000-000", "서울", Category.ITALIAN, false, member, null);

                em.persist(chinese);
                em.persist(italian);
            }
        }
    }
}
