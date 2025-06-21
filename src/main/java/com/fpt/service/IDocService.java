package com.fpt.service;

import com.fpt.dto.DocDTO;

import java.util.List;

public interface IDocService {
    List<DocDTO> getAll();
    DocDTO getById(Integer id);
    DocDTO create(DocDTO dto);
    DocDTO update(Integer id, DocDTO dto);
    void delete(Integer id);
}
