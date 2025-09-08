package com.hospital.Hospital_management.security;

import com.hospital.Hospital_management.entity.User;
import com.hospital.Hospital_management.exception.ResourceNotFoundException;
import com.hospital.Hospital_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {
    private final UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepo.findByUserName(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with username "+username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().toUpperCase())
                .build();
    }
}
