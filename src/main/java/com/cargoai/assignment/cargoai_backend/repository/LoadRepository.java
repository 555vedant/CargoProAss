package com.cargoai.assignment.cargoai_backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cargoai.assignment.cargoai_backend.entity.Load;

import java.util.UUID;

@Repository
public interface LoadRepository extends JpaRepository<Load, UUID> {
    Page<Load> findByShipperId(String shipperId, Pageable pageable);
}
