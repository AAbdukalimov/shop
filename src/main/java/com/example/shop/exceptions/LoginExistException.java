package com.example.shop.exceptions;

public class LoginExistException extends RuntimeException{

    public LoginExistException(String message){
        super(message);
    }

}
