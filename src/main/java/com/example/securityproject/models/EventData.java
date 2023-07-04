package com.example.securityproject.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Embedded;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EventData implements Serializable {
    private String requestUrl;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Details details;
    private String eventType;
    private String message;
}
