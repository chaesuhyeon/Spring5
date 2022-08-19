package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello") // '/hello'경로로 들어온 요청을 hello()메서드를 이용해서 처리 . GET메서드에 대한 매핑을 설정
    public String hello(Model model, @RequestParam(value = "name", required = false) String name){
        // Model 파라미터는 컨트롤러의 처리 결과를 뷰에 전달할 때 사용
        // @RequestParam : HTTP요청 파라미터의 값을 메서드의 파라미터로 전달할 때 사용 . name 요청 파라미터의 값을 name 파라미터에 전달
        model.addAttribute("greeting", "안녕하세요. " + name); // greeting이라는 모델 속성에 값을 설정 , 값으로는 "안녕하세요" 와 name 파라미터의 값을 연결한 문자열을 사용, JSP 코드는 이 속성을 이용해서 값을 출력 
        return "hello"; // 컨트롤러의 처리 결과를 보여줄 뷰 이름으로 "hello"사용
    }

}
