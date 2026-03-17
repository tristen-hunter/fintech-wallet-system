package dev.hunter.tristen.wallet_api.dto;

public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(){

    }

    public UserLoginDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

    // getters
    public String getEmail(){ return email; }

    public String getPassword(){ return password; }
}
