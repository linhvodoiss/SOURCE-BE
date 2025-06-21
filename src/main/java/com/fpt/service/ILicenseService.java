package com.fpt.service;

import com.fpt.dto.LicenseDTO;

import java.util.List;

public interface ILicenseService {
    List<LicenseDTO> getAll();
    LicenseDTO getById(int id);

    LicenseDTO create(LicenseDTO dto);
    LicenseDTO update(int id, LicenseDTO dto);
    void delete(int id);

}
