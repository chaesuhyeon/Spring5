package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import spring.RegisterRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegisterRequestValidator implements Validator {

    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;

    public RegisterRequestValidator(){
        pattern = Pattern.compile(emailRegExp);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest regReq = (RegisterRequest) target; // 검사 대상의 값을 구하기 위해 첫 번째 파라미터로 전달받은 target을 실제 타입으로 변환한 뒤에 검사
        if(regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()){
            errors.rejectValue("email" , "required");
        } else {
            Matcher matcher = pattern.matcher(regReq.getEmail());
            if(!matcher.matches()){
                errors.rejectValue("email" , "bad");
            }
        }


        // ValidationUtils.rejectIfEmptyOrWhitespace 사용할 때 validate 메서드에서 target을 넘기는 것 안해도 되는 이유
        // errors 객체의 getFieldValue("name") 메서드를 실행해서 커맨드 객체의 name 프로퍼티 값을 구함
        // 따라서 커맨드 객체를 직접 전달하지 않아도 값 검증을 할 수 있음 (validate 메서드에서 target을 넘기는 것 안해도 됨)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
        if(!regReq.getPassword().isEmpty()){
            if(!regReq.isPasswordEqualToConfirmPassword()){
                errors.rejectValue("confirmPassword" , "nomatch");
            }
        }
    }
}
