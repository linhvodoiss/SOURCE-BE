package com.fpt.controller;

import com.fpt.dto.LicenseDTO;
import com.fpt.service.ILicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final ILicenseService service;

    @GetMapping
    public List<LicenseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public LicenseDTO getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public LicenseDTO create(@RequestBody LicenseDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public LicenseDTO update(@PathVariable int id, @RequestBody LicenseDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
