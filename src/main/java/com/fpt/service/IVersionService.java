package com.fpt.service;

import com.fpt.dto.VersionDTO;

import java.util.List;

public interface IVersionService {
    List<VersionDTO> getAll();
    VersionDTO getById(Integer id);
    VersionDTO create(VersionDTO dto);
    VersionDTO update(Integer id, VersionDTO dto);
    void delete(Integer id);
}
