package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
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

    @PostMapping("/register/step2") //post방식만을 처리하기 때문에 'localhost:8080/sp5-chap11/register/step2' 치면 405에러
    public String handelStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree){
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
}

//@RequestParam의 속성
//value : String타입. HTTP요청 파라미터의 이름을 지정
//required : boolean타입. 필수 여부를 지정. 이 값이 true이면서 해당 요청 파라미터에 값이 없으면 예외 발생. 기본값은 true
//defaultValue : String타입. 요청 파라미터가 값이 없을 때 사용할 문자열 값을 지정. 기본값은 없음
