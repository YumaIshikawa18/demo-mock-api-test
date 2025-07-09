package com.yuzukiku.mockserver.application;

import com.yuzukiku.mockserver.domain.entity.Human;
import com.yuzukiku.mockserver.domain.repository.HumanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HumanService {
    private final HumanRepository humanRepository;

    public List<Human> getAll() {
        return humanRepository.findAll();
    }

    public List<Human> getByName(String name) {
        return humanRepository.findByName(name);
    }

    public Human create(Human human) {
        return humanRepository.save(human);
    }

}
