package com.hospital.Hospital_management.exception;

public class InvalidCredentialsExpection extends RuntimeException{
    public InvalidCredentialsExpection(String message){
        super(message);
    }
}
