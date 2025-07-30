package com.cargoai.assignment.cargoai_backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class BookingDTO {
    @JsonIgnore
    private UUID id;

    @NotNull(message = "Load ID must not be null")
    private UUID loadId;

    @NotBlank(message = "Transporter ID must not be blank")
    private String transporterId;

    @Positive(message = "Proposed rate must be positive")
    private double proposedRate;

    private String comment;

    @NotNull(message = "Status must not be null")
    @Pattern(regexp = "PENDING|ACCEPTED|REJECTED", message = "Status must be PENDING, ACCEPTED, or REJECTED")
    private String status;

    @NotNull(message = "Requested at must not be null")
    @PastOrPresent(message = "Requested at must be in the past or present")
    private LocalDateTime requestedAt;
}