package com.hospital.Hospital_management.service;

import com.hospital.Hospital_management.dto.AuthRequestDto;
import com.hospital.Hospital_management.dto.AuthResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.dto.UserRequestDto;
import com.hospital.Hospital_management.entity.User;
import com.hospital.Hospital_management.exception.InvalidCredentialsExpection;
import com.hospital.Hospital_management.exception.ResourceAlreadyExists;
import com.hospital.Hospital_management.exception.ResourceNotFoundException;
import com.hospital.Hospital_management.repository.UserRepository;
import com.hospital.Hospital_management.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseDto registerUser(UserRequestDto requestDto) {
        Optional<User> existinByUserName = userRepo.findByUserName(requestDto.getUserName());

        if (existinByUserName.isPresent()) {
            throw new ResourceAlreadyExists("User with username " + requestDto.getUserName() + " already Exists");
        }
        Optional<User> existingByUserEmail = userRepo.findByemail(requestDto.getEmail());

        if (existingByUserEmail.isPresent()) {
            throw new ResourceAlreadyExists("User with email " + requestDto.getEmail() + " already Exists");
        }

        User user= new User();
        user.setUserName(requestDto.getUserName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        if(requestDto.getRole()==null){
            user.setRole("PATIENT");
        }else{
            user.setRole(requestDto.getRole());
        }

        User savedUser=userRepo.save(user);

        return new ResponseDto("User successfully Crested with id "+savedUser.getId());
    }

    public AuthResponseDto login(AuthRequestDto requestDto){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUserName(),requestDto.getPassword())
            );
        }catch (Exception ex){

            throw new InvalidCredentialsExpection("Username or Password is incorrect");

        }

        User user=userRepo.findByUserName(requestDto.getUserName())
                .orElseThrow(()-> new ResourceNotFoundException("User not found with user name "+requestDto.getUserName()));

        return new AuthResponseDto(
                jwtUtil.generateJwtToken(user.getUserName(), user.getRole()),
                user.getRole()
        );
    }
}