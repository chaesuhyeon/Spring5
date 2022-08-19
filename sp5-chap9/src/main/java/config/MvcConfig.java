package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // @EnableWebMvc : 스프링 MVC 설정을 활성화 함. 스프링 MVC를 사용하는데 필요한 다양한 설정을 생성
public class MvcConfig implements WebMvcConfigurer {

    //////////// DispatcherServlet의 매핑 경로를 '/'로 주었을 때 , JSP/HTML/CSS 등을 올바르게 처리하기 위한 설정을 추가 ////////////
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////// JSP를 이용해서 컨트롤러의 실행결과를 보여주기 위한 설정을 추가 ////////////
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
        // registry.jsp() : JSP를 view 구현으로 사용할 수 있도록 해주는 설정
        // registry.jsp()의 첫번째 인자는 JSP 파일 경로를 찾을 때 사용할 접두어
        // 두번 째 인자는 접미사
        // view 이름의 앞과 뒤에 각각 접두어와 접미사를 뭍여서 최종적으로 사용할 JSP와 파일의 경로를 지정
    }
    //////////////////////////////////////////////////////////////////////////////
}
