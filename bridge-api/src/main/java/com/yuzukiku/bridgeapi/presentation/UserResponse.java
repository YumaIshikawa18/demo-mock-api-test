package com.yuzukiku.bridgeapi.presentation;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private int age;
    private List<String> hobbies;
}
