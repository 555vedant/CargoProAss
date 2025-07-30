package com.cargoai.assignment.cargoai_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;

    @Column(nullable = false)
    private String transporterId;

    @Column(nullable = false)
    private double proposedRate;

    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    public enum BookingStatus {
        PENDING, ACCEPTED, REJECTED
    }
}