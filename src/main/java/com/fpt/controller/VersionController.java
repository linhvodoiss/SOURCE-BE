package com.fpt.controller;

import com.fpt.dto.VersionDTO;
import com.fpt.service.IVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/versions")
@RequiredArgsConstructor
public class VersionController {

    private final IVersionService service;

    @GetMapping
    public List<VersionDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public VersionDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public VersionDTO create(@RequestBody VersionDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public VersionDTO update(@PathVariable Integer id, @RequestBody VersionDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
