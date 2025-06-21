package com.fpt.controller;

import com.fpt.dto.PaymentOrderDTO;
import com.fpt.service.IPaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class PaymentOrderController {

    private final IPaymentOrderService service;

    @GetMapping
    public List<PaymentOrderDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PaymentOrderDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public PaymentOrderDTO create(@RequestBody PaymentOrderDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PaymentOrderDTO update(@PathVariable Integer id, @RequestBody PaymentOrderDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
