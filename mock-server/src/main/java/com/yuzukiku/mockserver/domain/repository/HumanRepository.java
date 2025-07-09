package com.yuzukiku.mockserver.domain.repository;

import com.yuzukiku.mockserver.domain.entity.Human;

import java.util.List;
import java.util.Optional;

public interface HumanRepository {
    List<Human> findAll();

    List<Human> findByName(String name);

    Human save(Human human);

}
