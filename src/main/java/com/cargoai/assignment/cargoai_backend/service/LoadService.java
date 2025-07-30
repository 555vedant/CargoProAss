package com.cargoai.assignment.cargoai_backend.service;

import com.cargoai.assignment.cargoai_backend.dto.LoadDTO;
import com.cargoai.assignment.cargoai_backend.entity.Load;
import com.cargoai.assignment.cargoai_backend.repository.LoadRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoadService {

    @Autowired
    private LoadRepository loadRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public LoadDTO createLoad(LoadDTO loadDTO) {
        Load load = toEntity(loadDTO);
        load.setId(null); // Explicitly set ID to null for new entity
        entityManager.clear(); // Clear EntityManager to avoid stale state
        Load savedLoad = loadRepository.saveAndFlush(load); // Immediate flush
        return toDTO(savedLoad);
    }

    @Transactional
    public LoadDTO updateLoad(UUID id, LoadDTO loadDTO) {
        Load existingLoad = loadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Load not found with id: " + id));
        updateEntity(existingLoad, loadDTO);
        Load updatedLoad = loadRepository.save(existingLoad);
        return toDTO(updatedLoad);
    }

    @Transactional
    public void deleteLoad(UUID id) {
        if (!loadRepository.existsById(id)) {
            throw new RuntimeException("Load not found with id: " + id);
        }
        loadRepository.deleteById(id);
    }

    public LoadDTO getLoad(UUID id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Load not found with id: " + id));
        return toDTO(load);
    }

    public Page<LoadDTO> getAllLoads(Pageable pageable, String shipperId) {
        Page<Load> loads = (shipperId != null)
                ? loadRepository.findByShipperId(shipperId, pageable)
                : loadRepository.findAll(pageable);
        return loads.map(this::toDTO);
    }

    private Load toEntity(LoadDTO loadDTO) {
        Load load = new Load();
        load.setShipperId(loadDTO.getShipperId());
        if (loadDTO.getFacility() != null) {
            Load.Facility facility = new Load.Facility();
            facility.setLoadingPoint(loadDTO.getFacility().getLoadingPoint());
            facility.setUnloadingPoint(loadDTO.getFacility().getUnloadingPoint());
            facility.setLoadingDate(loadDTO.getFacility().getLoadingDate());
            facility.setUnloadingDate(loadDTO.getFacility().getUnloadingDate());
            load.setFacility(facility);
        }
        load.setProductType(loadDTO.getProductType());
        load.setTruckType(loadDTO.getTruckType());
        load.setNoOfTrucks(loadDTO.getNoOfTrucks());
        load.setWeight(loadDTO.getWeight());
        load.setComment(loadDTO.getComment());
        load.setDatePosted(loadDTO.getDatePosted());
        load.setStatus(loadDTO.getStatus() != null ? Load.LoadStatus.valueOf(loadDTO.getStatus()) : null);
        return load;
    }

    private LoadDTO toDTO(Load load) {
        LoadDTO loadDTO = new LoadDTO();
        loadDTO.setId(load.getId());
        loadDTO.setShipperId(load.getShipperId());
        if (load.getFacility() != null) {
            LoadDTO.FacilityDTO facilityDTO = new LoadDTO.FacilityDTO();
            facilityDTO.setLoadingPoint(load.getFacility().getLoadingPoint());
            facilityDTO.setUnloadingPoint(load.getFacility().getUnloadingPoint());
            facilityDTO.setLoadingDate(load.getFacility().getLoadingDate());
            facilityDTO.setUnloadingDate(load.getFacility().getUnloadingDate());
            loadDTO.setFacility(facilityDTO);
        }
        loadDTO.setProductType(load.getProductType());
        loadDTO.setTruckType(load.getTruckType());
        loadDTO.setNoOfTrucks(load.getNoOfTrucks());
        loadDTO.setWeight(load.getWeight());
        loadDTO.setComment(load.getComment());
        loadDTO.setDatePosted(load.getDatePosted());
        loadDTO.setStatus(load.getStatus() != null ? load.getStatus().name() : null);
        return loadDTO;
    }

    private void updateEntity(Load load, LoadDTO loadDTO) {
        load.setShipperId(loadDTO.getShipperId());
        if (loadDTO.getFacility() != null) {
            Load.Facility facility = load.getFacility() != null ? load.getFacility() : new Load.Facility();
            facility.setLoadingPoint(loadDTO.getFacility().getLoadingPoint());
            facility.setUnloadingPoint(loadDTO.getFacility().getUnloadingPoint());
            facility.setLoadingDate(loadDTO.getFacility().getLoadingDate());
            facility.setUnloadingDate(loadDTO.getFacility().getUnloadingDate());
            load.setFacility(facility);
        } else {
            load.setFacility(null);
        }
        load.setProductType(loadDTO.getProductType());
        load.setTruckType(loadDTO.getTruckType());
        load.setNoOfTrucks(loadDTO.getNoOfTrucks());
        load.setWeight(loadDTO.getWeight());
        load.setComment(loadDTO.getComment());
        load.setDatePosted(loadDTO.getDatePosted());
        load.setStatus(loadDTO.getStatus() != null ? Load.LoadStatus.valueOf(loadDTO.getStatus()) : null);
    }
}
