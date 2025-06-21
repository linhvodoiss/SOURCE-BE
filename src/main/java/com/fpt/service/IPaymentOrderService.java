package com.fpt.service;

import com.fpt.dto.PaymentOrderDTO;

import java.util.List;

public interface IPaymentOrderService {
    List<PaymentOrderDTO> getAll();
    PaymentOrderDTO getById(Integer id);
    PaymentOrderDTO create(PaymentOrderDTO dto);
    PaymentOrderDTO update(Integer id, PaymentOrderDTO dto);
    void delete(Integer id);
}
