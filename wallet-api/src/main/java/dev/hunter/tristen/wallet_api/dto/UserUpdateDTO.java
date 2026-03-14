package dev.hunter.tristen.wallet_api.dto;

import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String userName;

    public UserUpdateDTO(){

    }

    public UserUpdateDTO(String userName){
        this.userName = userName;
    }

    public String getUserName(){ return userName; }
    public void setUserName(String userName){ this.userName = userName; }
}