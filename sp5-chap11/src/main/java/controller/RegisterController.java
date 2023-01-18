package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    private MemberRegisterService memberRegisterService;

    public void setMemberRegisterService(MemberRegisterService memberRegisterService){
        this.memberRegisterService = memberRegisterService;
    }

    @RequestMapping("/register/step1")
    public String handleStep1(){
        return "register/step1";
    }

//    @PostMapping("/register/step2")
//    public String handleStep2(HttpServletRequest request){
//        String agreeParam = request.getParameter("agree");
//        if(agreeParam == null || !agreeParam.equals("true")){
//            return "register/step1";
//        }
//        return "register/step2";
//    }

//    @PostMapping("/register/step2") //post방식만을 처리하기 때문에 'localhost:8080/sp5-chap11/register/step2' 치면 405에러
//    public String handelStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree , Model model){
//        // agree 요청 파라미터의 값을 읽어와서 agreeVal 파라미터에 할당. 요청 파라미터의 값이 없으면 "false"문자열을 값으로 사용
//        if (!agree) {
//            return "register/step1";
//        }
//        model.addAttribute("registerRequest", new RegisterRequest());
//        return "register/step2";
//    }

    // Model 대신에 커맨드 객체를 파라미터로 추가
    @PostMapping("/register/step2")
    public String handelStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree , RegisterRequest registerRequest){ // 커맨드 객체(RegisterRequest) 앞에는 @ModelAttribute가 생략됨. 따라서 Model에 담아두지 않아도 view에서 사용할 수 있다
        // agree 요청 파라미터의 값을 읽어와서 agreeVal 파라미터에 할당. 요청 파라미터의 값이 없으면 "false"문자열을 값으로 사용
        if (!agree) {
            return "register/step1";
        }
        return "register/step2";
    }

    @GetMapping("/register/step2")
    public String handleStep2Get(){
        return "redirect:/register/step1";
    }

//    @PostMapping("/register/step3")
//    public String handelStep3(HttpServletRequest request){
//        String email = request.getParameter("email");
//        String name = request.getParameter("name");
//        String password = request.getParameter("password");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        RegisterRequest regReq = new RegisterRequest();
//        regReq.setEmail(email);
//        regReq.setName(name);
//
//        return null;
//    }
    @PostMapping("/register/step3")
    public String handelStep3(RegisterRequest regReq , Errors errors){ // Errors는 검증할 객체(RegisterRequest) 뒤에 위치해야한다. 그렇지 않으면 에러 발생
        new RegisterRequestValidator().validate(regReq, errors);
        if (errors.hasErrors()){
            return "register/step2";
        }

        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex){
            errors.rejectValue("email", "duplicate");
            return "register/step2";
        }
    }
}

//@RequestParam의 속성
//value : String타입. HTTP요청 파라미터의 이름을 지정
//required : boolean타입. 필수 여부를 지정. 이 값이 true이면서 해당 요청 파라미터에 값이 없으면 예외 발생. 기본값은 true
//defaultValue : String타입. 요청 파라미터가 값이 없을 때 사용할 문자열 값을 지정. 기본값은 없음
