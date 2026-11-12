package com.example.demo1_conference_talks_api.service;

import com.example.demo1_conference_talks_api.dto.UserRequestDto;
import com.example.demo1_conference_talks_api.entity.RoleEntity;
import com.example.demo1_conference_talks_api.entity.UserEntity;
import com.example.demo1_conference_talks_api.exception.DuplicateResourceException;
import com.example.demo1_conference_talks_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String addUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByUserName(userRequestDto.getUserName())) {
            throw new DuplicateResourceException("Username already exists: " + userRequestDto.getUserName());
        }

        UserEntity user = new UserEntity();
        user.setUserName(userRequestDto.getUserName());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        List<String> roleNames = (userRequestDto.getRoles() == null || userRequestDto.getRoles().isEmpty())
                ? List.of("USER")
                : userRequestDto.getRoles();

        List<RoleEntity> roleEntities = new ArrayList<>();
        for (String roleName : roleNames) {
            RoleEntity role = new RoleEntity();
            role.setRoleName(roleName.toUpperCase().replace("ROLE_", ""));
            role.setUser(user);
            roleEntities.add(role);
        }
        user.setRoles(roleEntities);

        UserEntity saved = userRepository.save(user);
        log.info("User created with id {}", saved.getId());
        return "User added successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String[] roles = userEntity.getRoles().stream()
                .map(r -> r.getRoleName().toUpperCase().replace("ROLE_", ""))
                .toArray(String[]::new);

        if (roles.length == 0) {
            roles = new String[]{"USER"};
        }

        return User.builder()
                .username(userEntity.getUserName())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();
    }
}
