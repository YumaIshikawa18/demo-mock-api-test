package com.yuzukiku.bridgeapi.infrastructure;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MockUserResponse {
    private UUID id;
    private String name;
    private int age;
    private List<String> hobbies;
}
