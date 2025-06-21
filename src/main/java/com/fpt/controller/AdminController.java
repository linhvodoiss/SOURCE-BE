package com.fpt.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fpt.dto.*;
import com.fpt.dto.filter.ProductFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fpt.entity.User;
import com.fpt.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Validated
public class AdminController {

    @Autowired
    private IUserService userService;
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer status
    ) {
        Page<User> entityPages = userService.getAllUser(pageable, search, status);
        List<UserListDTO> dtos = userService.convertToDto(entityPages.getContent());
        Page<UserListDTO> dtoPage = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> pageMap = mapper.convertValue(dtoPage, new TypeReference<>() {});

        pageMap.put("code", HttpServletResponse.SC_OK);
        pageMap.put("message", "Lấy danh sách người dùng thành công");

        return ResponseEntity.ok(pageMap);
    }
}
