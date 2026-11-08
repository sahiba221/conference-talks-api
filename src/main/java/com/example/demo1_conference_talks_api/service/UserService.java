package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.UserRequestDto;
import com.example.demo1_conference_talks_api.entity.UserEntity;
import com.example.demo1_conference_talks_api.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUserName(username).map(userEntity -> new UserDetails() {
//        }).orElseThrow(()-> new UsernameNotFoundException(""));
//
//    }

    public String addUserName( UserRequestDto userRequestDto){
        boolean exists = userRepository.existsByUserName(userRequestDto.getUserName());
//        user.setUserName(userRequestDto.getUserName());
//        userRepository.save(user);
return "";
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(userEntity.getUserName())
                .password(userEntity.getPassword())
                .build();

    }
}
