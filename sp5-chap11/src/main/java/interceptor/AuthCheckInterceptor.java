package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession(false);
        if (session != null){
            Object authInfo  = session.getAttribute("authInfo");
            if(authInfo != null){
                return  true; // HttpSession에 "authInfo"속성이 존재하면 true를 리턴
            }
        }
        response.sendRedirect(request.getContextPath() + "/login"); // 존재하지 않으면 리다이렉트 응답을 생성한 뒤 false를 리턴
        return false;
    }
    // preHandle()메서드에서 true를 리턴하면 컨트롤러를 실행하므로 로그인 상태면 컨트롤러를 실행한다.  반대로 false를 리턴하면 로그인 상태가 아니므로 지정한 경로로 리다이렉트한다.



}
