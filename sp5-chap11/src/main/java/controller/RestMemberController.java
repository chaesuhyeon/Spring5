package controller;

import jdk.vm.ci.code.Register;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import spring.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> member(@PathVariable("id") Long id, HttpServletResponse response) throws IOException{
        Member member = memberDao.selectById(id);
        if (member == null){
            // response.sendError(HttpServletResponse.SC_NOT_FOUND); // ResponseEntity사용 안했을 때, Return 타입 Member였을 때
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
        }
        // return member; // ResponseEntity사용 안했을 때, Return 타입 Member였을 때
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/api/members") // @RequestBody를 커맨드 객체에 붙이면 JSON 형식의 문자열을 해당 자바 객체로 변환
    public ResponseEntity<Object> newMember(@RequestBody @Valid RegisterRequest regReq , Errors errors) throws IOException {
//        if(errors.hasErrors()){
//            String errorCodes =
//                    errors.getAllErrors() // List<ObjectError>
//                            .stream()
//                            .map(error -> error.getCodes()[0]) // error은 ObjectError
//                            .collect(Collectors.joining(","));
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));
//        }
        try {
            Long newMemberId = registerService.regist(regReq);
            URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build();
        } catch (DuplicateMemberException dupEx) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

//    @PostMapping("/api/members") // @RequestBody를 커맨드 객체에 붙이면 JSON 형식의 문자열을 해당 자바 객체로 변환
//    public void newMember(@RequestBody @Valid RegisterRequest regReq, HttpServletResponse response) throws IOException{
//        try {
//            Long newMemberId = registerService.regist(regReq);
//            response.setHeader("Location" , "/api/members/" + newMemberId);
//            response.setStatus(HttpServletResponse.SC_CREATED);
//        } catch (DuplicateMemberException dupEx){
//            response.sendError(HttpServletResponse.SC_CONFLICT);
//        }
//    }




}
