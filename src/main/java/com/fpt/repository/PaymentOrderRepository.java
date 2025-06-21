package com.fpt.repository;

import com.fpt.entity.PaymentOrder;
import com.fpt.entity.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer> {

}
