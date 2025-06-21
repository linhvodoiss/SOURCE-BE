package com.fpt.service;

import com.fpt.dto.SubscriptionPackageDTO;

import java.util.List;

public interface ISubscriptionPackageService {
    List<SubscriptionPackageDTO> getAll();
    SubscriptionPackageDTO getById(Integer id);
    SubscriptionPackageDTO create(SubscriptionPackageDTO dto);
    SubscriptionPackageDTO update(Integer id, SubscriptionPackageDTO dto);
    void delete(Integer id);
}
