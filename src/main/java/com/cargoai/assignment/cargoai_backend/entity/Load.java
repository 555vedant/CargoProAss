package com.cargoai.assignment.cargoai_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "loads")
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String shipperId;

    @Embedded
    private Facility facility;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false)
    private String truckType;

    @Column(nullable = false)
    private int noOfTrucks;

    @Column(nullable = false)
    private double weight;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime datePosted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoadStatus status;

    public enum LoadStatus {
        POSTED, BOOKED, CANCELLED
    }

    @Embeddable
    @Data
    public static class Facility {
        @Column(nullable = false)
        private String loadingPoint;

        @Column(nullable = false)
        private String unloadingPoint;

        @Column(nullable = false)
        private LocalDateTime loadingDate;

        @Column(nullable = false)
        private LocalDateTime unloadingDate;
    }
}