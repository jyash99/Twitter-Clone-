package tech.codingclub.helix.entity;

public class LoginResponse {
    public Long id;
    public String message;
    public Boolean is_Login;

    public LoginResponse(Long id, String message, Boolean is_Login) {
        this.id = id;
        this.message = message;
        this.is_Login = is_Login;
    }

    public LoginResponse() {

    }
}
