package com.hospital.Hospital_management.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String userName;
    private String email;
    private String password;
    private String role;
}
