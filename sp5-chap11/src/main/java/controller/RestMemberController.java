package controller;

import jdk.vm.ci.code.Register;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class RestMemberController {
    private MemberDao memberDao;
    private MemberRegisterService registerService;

    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }
    public void setRegisterService(MemberRegisterService registerService){
        this.registerService = registerService;
    }

    @GetMapping("/api/members")
    public List<Member> members(){
        return memberDao.selectAll();
    }

    @GetMapping("/api/members/{id}")
    public Member member(@PathVariable("id") Long id, HttpServletResponse response) throws IOException{
        Member member = memberDao.selectById(id);
        if (member == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return member;
    }

    @PostMapping("/api/members") // @RequestBody를 커맨드 객체에 붙이면 JSON 형식의 문자열을 해당 자바 객체로 변환
    public void newMember(@RequestBody @Valid RegisterRequest regReq, HttpServletResponse response) throws IOException{
        try {
            Long newMemberId = registerService.regist(regReq);
            response.setHeader("Location" , "/api/members/" + newMemberId);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DuplicateMemberException dupEx){
            response.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }



}
