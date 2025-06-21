package com.fpt.service;

import com.fpt.dto.LicenseDTO;
import com.fpt.entity.License;
import com.fpt.repository.LicenseRepository;
import com.fpt.repository.SubscriptionPackageRepository;
import com.fpt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LicenseService implements ILicenseService {

    private final LicenseRepository licenseRepository;
    private final UserRepository userRepository;
    private final SubscriptionPackageRepository subscriptionRepository;

    @Override
    public List<LicenseDTO> getAll() {
        return licenseRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public LicenseDTO getById(int id) {
        return licenseRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("License not found"));
    }

    @Override
    public LicenseDTO create(LicenseDTO dto) {
        return toDto(licenseRepository.save(toEntity(dto)));
    }

    @Override
    public LicenseDTO update(int id, LicenseDTO dto) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found"));

        license.setLicenseKey(dto.getLicenseKey());
        license.setExpiryDate(dto.getExpiryDate());
        license.setIp(dto.getIp());

        return toDto(licenseRepository.save(license));
    }

    @Override
    public void delete(int id) {
        licenseRepository.deleteById(id);
    }

    private LicenseDTO toDto(License entity) {
        return LicenseDTO.builder()
                .id(entity.getId())
                .licenseKey(entity.getLicenseKey())
                .expiryDate(entity.getExpiryDate())
                .ip(entity.getIp())
                .userId(entity.getUser().getId())
                .subscriptionId(entity.getSubscriptionPackage().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private License toEntity(LicenseDTO dto) {
        return License.builder()
                .licenseKey(dto.getLicenseKey())
                .expiryDate(dto.getExpiryDate())
                .ip(dto.getIp())
                .user(userRepository.findById(dto.getUserId()).orElseThrow())
                .subscriptionPackage(subscriptionRepository.findById(dto.getSubscriptionId()).orElseThrow())
                .build();
    }
}
