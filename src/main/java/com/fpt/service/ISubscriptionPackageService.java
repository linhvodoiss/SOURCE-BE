package com.fpt.service;

import com.fpt.dto.SubscriptionPackageDTO;
import com.fpt.dto.UserListDTO;
import com.fpt.entity.SubscriptionPackage;
import com.fpt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISubscriptionPackageService {
    Page<SubscriptionPackage> getAllPackage(Pageable pageable, String search);
    List<SubscriptionPackageDTO> convertToDto(List<SubscriptionPackage> data);
    List<SubscriptionPackageDTO> getAll();
    SubscriptionPackageDTO getById(Integer id);
    SubscriptionPackageDTO create(SubscriptionPackageDTO dto);
    SubscriptionPackageDTO update(Integer id, SubscriptionPackageDTO dto);
    void delete(Integer id);
}
