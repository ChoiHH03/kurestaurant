package miniproject.KUrestaurant.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import miniproject.KUrestaurant.domain.Member;
import miniproject.KUrestaurant.domain.MemberType;
import miniproject.KUrestaurant.web.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ConsumerAuthorizationCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("손님 권한 인터셉터 실행 {}", requestURI);
        Member member = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

        if (member.getMemberType() != MemberType.CUSTOMER) {
            log.info("손님이 아닙니다.");
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
