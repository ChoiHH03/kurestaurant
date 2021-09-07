package miniproject.KUrestaurant.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Category;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.RestaurantSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
                .where(nameEq(cond.getName()),categoryEq(cond.getCategory()))
                .fetch();
    }

    @Override
    public Page<Restaurant> searchPage(RestaurantSearchCond cond, Pageable pageable) {
        //fetchResuts 사용
        QueryResults<Restaurant> results = queryFactory
                .selectFrom(restaurant)
                .where(allEq(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Restaurant> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Restaurant> sortByEvalNum(RestaurantSearchCond cond, Pageable pageable) {
        //fetch와 fetchCount 별도의 쿼리로 최적화
        List<Restaurant> content = queryFactory
                .selectFrom(restaurant)
                .where(allEq(cond))
                .orderBy(restaurant.eval_num.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(restaurant)
                .where(allEq(cond))
                .orderBy(restaurant.eval_num.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
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
