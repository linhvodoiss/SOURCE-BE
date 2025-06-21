package com.fpt.controller;

import com.fpt.dto.SubscriptionPackageDTO;
import com.fpt.service.ISubscriptionPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionPackageController {

    private final ISubscriptionPackageService service;

    @GetMapping
    public List<SubscriptionPackageDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SubscriptionPackageDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public SubscriptionPackageDTO create(@RequestBody SubscriptionPackageDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SubscriptionPackageDTO update(@PathVariable Integer id, @RequestBody SubscriptionPackageDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
