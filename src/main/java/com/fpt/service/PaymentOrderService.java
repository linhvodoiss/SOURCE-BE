package com.fpt.service;

import com.fpt.dto.PaymentOrderDTO;
import com.fpt.entity.PaymentOrder;
import com.fpt.entity.SubscriptionPackage;
import com.fpt.repository.PaymentOrderRepository;
import com.fpt.repository.SubscriptionPackageRepository;
import com.fpt.repository.UserRepository;
import com.fpt.service.IPaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentOrderService implements IPaymentOrderService {

    private final PaymentOrderRepository repository;
    private final UserRepository userRepository;
    private final SubscriptionPackageRepository subscriptionRepository;

    @Override
    public List<PaymentOrderDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public PaymentOrderDTO getById(int id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));
    }

    @Override
    public PaymentOrderDTO create(PaymentOrderDTO dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public PaymentOrderDTO update(int id, PaymentOrderDTO dto) {
        PaymentOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        order.setOrderId(dto.getOrderId());
        order.setPaymentLink(dto.getPaymentLink());
        order.setAmount(dto.getAmount());
        order.setBillingCycle(SubscriptionPackage.BillingCycle.valueOf(dto.getBillingCycle()));
        order.setTarget(dto.getTarget());
        order.setPaymentStatus(PaymentOrder.PaymentStatus.valueOf(dto.getPaymentStatus()));

        return toDto(repository.save(order));
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    private PaymentOrderDTO toDto(PaymentOrder entity) {
        return PaymentOrderDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .paymentLink(entity.getPaymentLink())
                .amount(entity.getAmount())
                .billingCycle(entity.getBillingCycle().name())
                .target(entity.getTarget())
                .paymentStatus(entity.getPaymentStatus().name())
                .userId(entity.getUser().getId())
                .subscriptionId(entity.getSubscriptionPackage().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private PaymentOrder toEntity(PaymentOrderDTO dto) {
        return PaymentOrder.builder()
                .orderId(dto.getOrderId())
                .paymentLink(dto.getPaymentLink())
                .amount(dto.getAmount())
                .billingCycle(SubscriptionPackage.BillingCycle.valueOf(dto.getBillingCycle()))
                .target(dto.getTarget())
                .paymentStatus(PaymentOrder.PaymentStatus.valueOf(dto.getPaymentStatus()))
                .user(userRepository.findById(dto.getUserId()).orElseThrow())
                .subscriptionPackage(subscriptionRepository.findById(dto.getSubscriptionId()).orElseThrow())
                .build();
    }
}
