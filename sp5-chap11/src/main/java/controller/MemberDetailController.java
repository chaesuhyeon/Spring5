package controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;

@Controller
public class MemberDetailController {
    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    @GetMapping("/members/{id}")
    public String detail(@PathVariable("id") Long memId, Model model){
        Member member = memberDao.selectById(memId);
        if(member == null){
            throw new MemberNotFoundException();
        }
        model.addAttribute("member", member);
        return "member/memberDetail";
    }

    @ExceptionHandler(TypeMismatchException.class)
    public String handleTypeMismatchException(){
        return "member/invalidId";
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public String handleNotFoundException(){
        return "member/noMember";
    }

}

// 컨트롤러 클래스에 @ExceptionHandler 애노테이션을 적용하면 해당 컨트롤러에서 발생한 익셉션만을 처리
// 여러 컨트롤러에서 동일하게 처리할 익셉션이 발생하면 @ControllerAdvice 애노테이션을 이용해서 중복을 없앨 수 있음
// @ControllerAdvice 적용 클래스가 동작하려면 해당 클래스를 스프링에 빈으로 등록해야 함