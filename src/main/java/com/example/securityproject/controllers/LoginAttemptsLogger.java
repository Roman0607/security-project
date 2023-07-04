package com.example.securityproject.controllers;

import com.example.securityproject.models.Details;
import com.example.securityproject.models.EventData;
import com.example.securityproject.repository.CustomAuditEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginAttemptsLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger("AuditLogger");
    @Autowired
    private CustomAuditEventRepository customAuditEventRepository;

    @EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {
        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
        WebAuthenticationDetails auditDetails = (WebAuthenticationDetails) auditEvent.getData().get("details");
        Details details = new Details(auditDetails.getRemoteAddress(), auditDetails.getSessionId());
        String type = (String) auditEvent.getData().get("type");
        String message = (String) auditEvent.getData().get("message");
        String requestUrl = (String) auditEvent.getData().get("requestUrl");

        com.example.securityproject.models.AuditEvent event = com.example.securityproject.models.AuditEvent.builder()
                .principal(auditEvent.getPrincipal())
                .timestamp(auditEvent.getTimestamp())
                .data(new EventData(requestUrl, details, type, message))
                .type(auditEvent.getType())
                .build();

        LOGGER.info("An audit event was received");
        System.out.println("Principal " + auditEvent.getPrincipal() + " -  " + auditEvent.getType());

        System.out.println("Remote IP address: " + auditDetails.getRemoteAddress());

        System.out.println("Session Id: " + details.getSessionId());
        customAuditEventRepository.save(event);
    }
}
