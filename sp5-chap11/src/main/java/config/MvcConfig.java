package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import interceptor.AuthCheckInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }

    @Override // addViewControllers 이용하면 컨트롤러 구현 없이 요청 경로와 뷰 이름을 연결할 수 있음
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/main").setViewName("main");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authCheckInterceptor())
                .addPathPatterns("/edit/**")
                .excludePathPatterns("/edit/help/**"); //addPathPatterns메서드에 지정한 경로 패턴 중 일부를 제외하고 싶을 때 사용
    }

    @Override
    public void extendMessageConverters(
            List<HttpMessageConverter<?>> converters){
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                                    .json()
                                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                    .build();
        converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasenames("message.label" , "message.error");
        // message 패키지에 속한 label프로퍼티 파일로부터 메세지 읽어온다고 설정
        // setBasenames()는 가변 인자이므로 사용할 메시지 프로퍼티 목록을 전달하라 수 있음
        ms.setDefaultEncoding("UTF-8"); // label.properties파일은 UTF-8 인코딩을 사용하므로 지정
        return ms;
    }

    @Bean
    public AuthCheckInterceptor authCheckInterceptor(){
        return new AuthCheckInterceptor();
    }

}