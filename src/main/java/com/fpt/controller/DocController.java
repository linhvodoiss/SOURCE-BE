package com.fpt.controller;

import com.fpt.dto.DocDTO;
import com.fpt.service.IDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docs")
@RequiredArgsConstructor
public class DocController {

    private final IDocService service;

    @GetMapping
    public List<DocDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DocDTO getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public DocDTO create(@RequestBody DocDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public DocDTO update(@PathVariable int id, @RequestBody DocDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
