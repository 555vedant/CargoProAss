package com.cargoai.assignment.cargoai_backend.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cargoai.assignment.cargoai_backend.dto.LoadDTO;
import com.cargoai.assignment.cargoai_backend.service.LoadService;

import java.util.UUID;

@RestController
@RequestMapping("/api/loads")
public class LoadController {
    @Autowired
    private LoadService loadService;

    @PostMapping
    public ResponseEntity<LoadDTO> createLoad(@Valid @RequestBody LoadDTO loadDTO) {
        return ResponseEntity.ok(loadService.createLoad(loadDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoadDTO> updateLoad(@PathVariable UUID id, @Valid @RequestBody LoadDTO loadDTO) {
        return ResponseEntity.ok(loadService.updateLoad(id, loadDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoad(@PathVariable UUID id) {
        loadService.deleteLoad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoadDTO> getLoad(@PathVariable UUID id) {
        return ResponseEntity.ok(loadService.getLoad(id));
    }

    @GetMapping
    public ResponseEntity<Page<LoadDTO>> getAllLoads(Pageable pageable, @RequestParam(required = false) String shipperId) {
        return ResponseEntity.ok(loadService.getAllLoads(pageable, shipperId));
    }
}
