package com.fpt.service;

import com.fpt.dto.LicenseDTO;

import java.util.List;

public interface ILicenseService {
    List<LicenseDTO> getAll();
    LicenseDTO getById(Integer id);

    LicenseDTO create(LicenseDTO dto);
    LicenseDTO update(Integer id, LicenseDTO dto);
    void delete(Integer id);

}
