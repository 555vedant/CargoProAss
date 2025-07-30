package com.cargoai.assignment.cargoai_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadDTO {
    @JsonIgnore // ignore id during deserialization
    private UUID id;

    @NotBlank(message = "Shipper ID must not be blank")
    private String shipperId;

    @NotNull(message = "Facility must not be null")
    private FacilityDTO facility;

    @NotBlank(message = "Product type must not be blank")
    private String productType;

    @NotBlank(message = "Truck type must not be blank")
    private String truckType;

    @Positive(message = "Number of trucks must be positive")
    private int noOfTrucks;

    @Positive(message = "Weight must be positive")
    private double weight;

    private String comment;

    @NotNull(message = "Date posted must not be null")
    @PastOrPresent(message = "Date posted must be in the past or present")
    private LocalDateTime datePosted;

    @NotNull(message = "Status must not be null")
    @Pattern(regexp = "POSTED|BOOKED|CANCELLED", message = "Status must be POSTED, BOOKED, or CANCELLED")
    private String status;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FacilityDTO {
        @NotBlank(message = "Loading point must not be blank")
        private String loadingPoint;

        @NotBlank(message = "Unloading point must not be blank")
        private String unloadingPoint;

        @NotNull(message = "Loading date must not be null")
        @FutureOrPresent(message = "Loading date must be in the present or future")
        private LocalDateTime loadingDate;

        @NotNull(message = "Unloading date must not be null")
        @FutureOrPresent(message = "Unloading date must be in the present or future")
        private LocalDateTime unloadingDate;
    }
}