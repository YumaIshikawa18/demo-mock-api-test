package com.yuzukiku.mockserver.presentation;

import com.yuzukiku.mockserver.application.HumanService;
import com.yuzukiku.mockserver.domain.entity.Human;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/humans")
@RequiredArgsConstructor
public class HumanController {
    private final HumanService humanService;

    @PostMapping
    public List<Human> getAll() {
        return humanService.getAll();
    }

    @PostMapping("/name")
    public List<Human> getByName(@RequestBody NameRequest request) {
        return humanService.getByName(request.getName());
    }

    @PostMapping("/create")
    public Human create(@RequestBody Human human) {
        return humanService.create(human);
    }

}
