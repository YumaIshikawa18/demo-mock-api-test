package com.yuzukiku.bridgeapi.presentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private String name;
    private int age;
    private List<String> hobbies;
}
