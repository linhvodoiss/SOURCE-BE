package com.fpt.service;

import com.fpt.dto.SubscriptionPackageDTO;
import com.fpt.entity.SubscriptionPackage;
import com.fpt.repository.SubscriptionPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionPackageService implements ISubscriptionPackageService {

    private final SubscriptionPackageRepository repository;

    @Override
    public Page<SubscriptionPackage> getAllPackage(Pageable pageable, String search) {
        return null;
    }

    @Override
    public List<SubscriptionPackageDTO> convertToDto(List<SubscriptionPackage> data) {
        return List.of();
    }

    @Override
    public List<SubscriptionPackageDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public SubscriptionPackageDTO getById(Integer id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
    }

    @Override
    public SubscriptionPackageDTO create(SubscriptionPackageDTO dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public SubscriptionPackageDTO update(Integer id, SubscriptionPackageDTO dto) {
        SubscriptionPackage entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDiscount(dto.getDiscount());
        entity.setBillingCycle(SubscriptionPackage.BillingCycle.valueOf(dto.getBillingCycle()));
        entity.setIsActive(dto.getIsActive());
        entity.setOptions(dto.getOptions());
        entity.setSimulatedCount(dto.getSimulatedCount());

        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private SubscriptionPackageDTO toDto(SubscriptionPackage entity) {
        return SubscriptionPackageDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .billingCycle(entity.getBillingCycle().name())
                .isActive(entity.getIsActive())
                .options(entity.getOptions())
                .simulatedCount(entity.getSimulatedCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private SubscriptionPackage toEntity(SubscriptionPackageDTO dto) {
        return SubscriptionPackage.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .discount(dto.getDiscount())
                .billingCycle(SubscriptionPackage.BillingCycle.valueOf(dto.getBillingCycle()))
                .isActive(dto.getIsActive())
                .options(dto.getOptions())
                .simulatedCount(dto.getSimulatedCount())
                .build();
    }
}
