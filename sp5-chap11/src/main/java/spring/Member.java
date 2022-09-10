package spring;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Member {

    private Long id;
    private String email;
    @JsonIgnore // JSON응답에 password같은 민감한 정보를 포함시키지 않기 위해 사용
    private String password;
    private String name;
    //@JsonFormat(shape = JsonFormat.Shape.STRING) // ISO-8601형식으로 변환
    //@JsonFormat(pattern = "yyyyMMddHHmmss") --> 매번 이렇게 적어줄 수 없으므로 MappingJackson2HttpMessageConverter이용 (MvcConfig 참고)
    private LocalDateTime registerDateTime;

    public Member(String email, String password,
                  String name, LocalDateTime regDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registerDateTime = regDateTime;
    }

    void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }

    public boolean matchPassword(String password){
        return this.password.equals(password);
    }

}