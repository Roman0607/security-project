package com.example.securityproject.repository;

import com.example.securityproject.models.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomAuditEventRepository extends JpaRepository<AuditEvent, Long> {
}
