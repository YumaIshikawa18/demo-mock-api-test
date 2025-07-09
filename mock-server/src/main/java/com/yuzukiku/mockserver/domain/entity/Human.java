package com.yuzukiku.mockserver.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "human")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Human {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private int age;
    private String address;
    @ElementCollection
    private List<String> hobbies;
    private String email;
}
