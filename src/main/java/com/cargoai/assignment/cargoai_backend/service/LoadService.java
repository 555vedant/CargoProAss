package com.cargoai.assignment.cargoai_backend.service;

import com.cargoai.assignment.cargoai_backend.dto.LoadDTO;
import com.cargoai.assignment.cargoai_backend.entity.Load;
import com.cargoai.assignment.cargoai_backend.repository.LoadRepository;
import com.cargoai.assignment.cargoai_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoadService {
    @Autowired
    private LoadRepository loadRepository;

    public LoadDTO createLoad(LoadDTO loadDTO) {
        if (loadDTO.getFacility() == null) {
            throw new IllegalArgumentException("Facility must not be null");
        }
        Load load = convertToEntity(loadDTO);
        load = loadRepository.save(load);
        return convertToDTO(load);
    }

    public LoadDTO updateLoad(UUID id, LoadDTO loadDTO) {
        if (loadDTO.getFacility() == null) {
            throw new IllegalArgumentException("Facility must not be null");
        }
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        updateEntityFromDTO(load, loadDTO);
        load = loadRepository.save(load);
        return convertToDTO(load);
    }

    public void deleteLoad(UUID id) {
        loadRepository.deleteById(id);
    }

    public LoadDTO getLoad(UUID id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Load not found"));
        return convertToDTO(load);
    }

    public Page<LoadDTO> getAllLoads(Pageable pageable, String shipperId) {
        Page<Load> loads = shipperId != null
                ? loadRepository.findByShipperId(shipperId, pageable)
                : loadRepository.findAll(pageable);
        return loads.map(this::convertToDTO);
    }

    private LoadDTO convertToDTO(Load load) {
        LoadDTO loadDTO = new LoadDTO();
        loadDTO.setId(load.getId());
        loadDTO.setShipperId(load.getShipperId());
        LoadDTO.FacilityDTO facilityDTO = new LoadDTO.FacilityDTO();
        facilityDTO.setLoadingPoint(load.getFacility().getLoadingPoint());
        facilityDTO.setUnloadingPoint(load.getFacility().getUnloadingPoint());
        facilityDTO.setLoadingDate(load.getFacility().getLoadingDate());
        facilityDTO.setUnloadingDate(load.getFacility().getUnloadingDate());
        loadDTO.setFacility(facilityDTO);
        loadDTO.setProductType(load.getProductType());
        loadDTO.setTruckType(load.getTruckType());
        loadDTO.setNoOfTrucks(load.getNoOfTrucks());
        loadDTO.setWeight(load.getWeight());
        loadDTO.setComment(load.getComment());
        loadDTO.setDatePosted(load.getDatePosted());
        loadDTO.setStatus(load.getStatus().name());
        return loadDTO;
    }

    private Load convertToEntity(LoadDTO loadDTO) {
        Load load = new Load();
        load.setId(loadDTO.getId());
        load.setShipperId(loadDTO.getShipperId());
        Load.Facility facility = new Load.Facility();
        facility.setLoadingPoint(loadDTO.getFacility().getLoadingPoint());
        facility.setUnloadingPoint(loadDTO.getFacility().getUnloadingPoint());
        facility.setLoadingDate(loadDTO.getFacility().getLoadingDate());
        facility.setUnloadingDate(loadDTO.getFacility().getUnloadingDate());
        load.setFacility(facility);
        load.setProductType(loadDTO.getProductType());
        load.setTruckType(loadDTO.getTruckType());
        load.setNoOfTrucks(loadDTO.getNoOfTrucks());
        load.setWeight(loadDTO.getWeight());
        load.setComment(loadDTO.getComment());
        load.setDatePosted(loadDTO.getDatePosted());
        load.setStatus(Load.LoadStatus.valueOf(loadDTO.getStatus()));
        return load;
    }

    private void updateEntityFromDTO(Load load, LoadDTO loadDTO) {
        load.setShipperId(loadDTO.getShipperId());
        Load.Facility facility = load.getFacility() != null ? load.getFacility() : new Load.Facility();
        facility.setLoadingPoint(loadDTO.getFacility().getLoadingPoint());
        facility.setUnloadingPoint(loadDTO.getFacility().getUnloadingPoint());
        facility.setLoadingDate(loadDTO.getFacility().getLoadingDate());
        facility.setUnloadingDate(loadDTO.getFacility().getUnloadingDate());
        load.setFacility(facility);
        load.setProductType(loadDTO.getProductType());
        load.setTruckType(loadDTO.getTruckType());
        load.setNoOfTrucks(loadDTO.getNoOfTrucks());
        load.setWeight(loadDTO.getWeight());
        load.setComment(loadDTO.getComment());
        load.setDatePosted(loadDTO.getDatePosted());
        load.setStatus(Load.LoadStatus.valueOf(loadDTO.getStatus()));
    }
}