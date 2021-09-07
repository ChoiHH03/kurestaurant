package miniproject.KUrestaurant.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Category;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.RestaurantSearchCond;

import java.util.List;

import static miniproject.KUrestaurant.domain.QRestaurant.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Restaurant> search(RestaurantSearchCond cond) {
        return queryFactory
                .selectFrom(restaurant)
                .where(allEq(cond))
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? restaurant.name.contains(name) : null;
    }

    private BooleanExpression categoryEq(Category category) {
        return (category != null) ? restaurant.category.eq(category) : null;
    }

    private BooleanExpression allEq(RestaurantSearchCond cond) {
        return nameEq(cond.getName()).and(categoryEq(cond.getCategory()));
    }
}
