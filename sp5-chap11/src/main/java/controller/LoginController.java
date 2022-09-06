package controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    private AuthService authService;

    public void setAuthService(AuthService authService){
        this.authService = authService;
    }

    @GetMapping
    public String form(LoginCommand loginCommand , @CookieValue(value = "REMEMBER", required = false) Cookie rCookie){
        // 이름이 "REMEMBER"인 쿠키를 Cookie 타입으로 전달받는다. 지정한 이름을 가진 쿠기가 존재하지 않을 수도 있다면 required 속성값을 false로 지정

        if(rCookie != null){
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }

    @PostMapping
    public String submit(LoginCommand loginCommand , Errors errors , HttpSession session, HttpServletResponse response){
        new LoginCommandValidator().validate(loginCommand, errors);
        if(errors.hasErrors()){
            return "login/loginForm";
        }

        try {
            AuthInfo authInfo = authService.authenticate(
                    loginCommand.getEmail(), loginCommand.getPassword()
            );
            session.setAttribute("authInfo", authInfo);

            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
            rememberCookie.setPath("/");
            if (loginCommand.isRememberEmail()){
                rememberCookie.setMaxAge(60*60*24*30); // 30일동안 유지
            } else {
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);

            //TODO세션에 authInfo 저장해야함
            return "login/loginSuccess";
        } catch (WrongIdPasswordException e){
            errors.reject("idPasswordNotMatching");
            return "login/loginForm";
        }
    }

}
