package com.fpt.repository;

import com.fpt.entity.License;
import com.fpt.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Integer> {

}
