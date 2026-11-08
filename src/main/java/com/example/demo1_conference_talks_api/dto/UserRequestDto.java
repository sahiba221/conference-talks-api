package com.example.demo1_conference_talks_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String userName;
    private String password;
    private List<String> roles;

}
