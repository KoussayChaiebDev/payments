package com.progressoft.payments.domain.repository;

import com.progressoft.payments.domain.clientSide.Payment;
import com.progressoft.payments.domain.entities.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Entity, String>,JpaSpecificationExecutor<Entity> {
}




