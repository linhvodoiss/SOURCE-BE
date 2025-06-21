package com.fpt.service;

import com.fpt.dto.PaymentOrderDTO;

import java.util.List;

public interface IPaymentOrderService {
    List<PaymentOrderDTO> getAll();
    PaymentOrderDTO getById(int id);
    PaymentOrderDTO create(PaymentOrderDTO dto);
    PaymentOrderDTO update(int id, PaymentOrderDTO dto);
    void delete(int id);
}
