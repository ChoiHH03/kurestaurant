package miniproject.KUrestaurant.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import miniproject.KUrestaurant.domain.Category;
import miniproject.KUrestaurant.domain.Restaurant;
import miniproject.KUrestaurant.domain.RestaurantSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
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
    public Page<Restaurant> searchAndSort(RestaurantSearchCond cond, Pageable pageable) {
        List<OrderSpecifier> orderSpecifiers = orderCondition(pageable);
        //fetchResuts 사용
        QueryResults<Restaurant> results = queryFactory
                .selectFrom(restaurant)
                .where(nameEq(cond.getName()),categoryEq(cond.getCategory()))
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Restaurant> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private List<OrderSpecifier> orderCondition(Pageable pageable) {
        List<OrderSpecifier> result = new ArrayList<>();
        for (Sort.Order o :pageable.getSort()) {
            PathBuilder<Restaurant> orderByExpression = new PathBuilder<Restaurant>(Restaurant.class, "restaurant");
            result.add(new OrderSpecifier(Order.DESC,orderByExpression.get(o.getProperty())));
        }
        return result;
    }

    @Override
    public Page<Restaurant> sortByEvalNum(RestaurantSearchCond cond, Pageable pageable) {
        OrderSpecifier<Long> asc = restaurant.eval_num.asc();
        //fetch와 fetchCount 별도의 쿼리로 최적화
        List<Restaurant> content = queryFactory
                .selectFrom(restaurant)
                .where(nameEq(cond.getName()),categoryEq(cond.getCategory()))
                .orderBy(restaurant.eval_num.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(restaurant)
                .where(nameEq(cond.getName()),categoryEq(cond.getCategory()))
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
}
