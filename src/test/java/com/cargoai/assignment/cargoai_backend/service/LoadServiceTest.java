
package com.cargoai.assignment.cargoai_backend.service;

import com.cargoai.assignment.cargoai_backend.dto.LoadDTO;
import com.cargoai.assignment.cargoai_backend.entity.Load;
import com.cargoai.assignment.cargoai_backend.repository.LoadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadServiceTest {

    @Mock
    private LoadRepository loadRepository;

    @InjectMocks
    private LoadService loadService;

    private LoadDTO loadDTO;
    private Load load;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        loadDTO = new LoadDTO();
        loadDTO.setShipperId("SHIP123");
        LoadDTO.FacilityDTO facilityDTO = new LoadDTO.FacilityDTO();
        facilityDTO.setLoadingPoint("Delhi Warehouse");
        facilityDTO.setUnloadingPoint("Mumbai Depot");
        facilityDTO.setLoadingDate(LocalDateTime.of(2025, 7, 31, 10, 0));
        facilityDTO.setUnloadingDate(LocalDateTime.of(2025, 8, 1, 18, 0));
        loadDTO.setFacility(facilityDTO);
        loadDTO.setProductType("Electronics");
        loadDTO.setTruckType("Refrigerated");
        loadDTO.setNoOfTrucks(3);
        loadDTO.setWeight(4500.75);
        loadDTO.setComment("Handle with care");
        loadDTO.setDatePosted(LocalDateTime.of(2025, 7, 30, 15, 30));
        loadDTO.setStatus("POSTED");

        load = new Load();
        load.setId(id);
        load.setShipperId("SHIP123");
        Load.Facility facility = new Load.Facility();
        facility.setLoadingPoint("Delhi Warehouse");
        facility.setUnloadingPoint("Mumbai Depot");
        facility.setLoadingDate(LocalDateTime.of(2025, 7, 31, 10, 0));
        facility.setUnloadingDate(LocalDateTime.of(2025, 8, 1, 18, 0));
        load.setFacility(facility);
        load.setProductType("Electronics");
        load.setTruckType("Refrigerated");
        load.setNoOfTrucks(3);
        load.setWeight(4500.75);
        load.setComment("Handle with care");
        load.setDatePosted(LocalDateTime.of(2025, 7, 30, 15, 30));
        load.setStatus(Load.LoadStatus.POSTED);
    }

    @Test
    void testCreateLoad_Success() {
        // Arrange
        when(loadRepository.saveAndFlush(any(Load.class))).thenReturn(load);

        // Act
        LoadDTO result = loadService.createLoad(loadDTO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("SHIP123", result.getShipperId());
        assertEquals("Delhi Warehouse", result.getFacility().getLoadingPoint());
        assertEquals("Mumbai Depot", result.getFacility().getUnloadingPoint());
        assertEquals("Electronics", result.getProductType());
        assertEquals("Refrigerated", result.getTruckType());
        assertEquals(3, result.getNoOfTrucks());
        assertEquals(4500.75, result.getWeight());
        assertEquals("Handle with care", result.getComment());
        assertEquals("POSTED", result.getStatus());
        verify(loadRepository, times(1)).saveAndFlush(any(Load.class));
    }

    @Test
    void testCreateLoad_NullFacility() {
        // Arrange
        loadDTO.setFacility(null);
        load.setFacility(null);
        when(loadRepository.saveAndFlush(any(Load.class))).thenReturn(load);

        // Act
        LoadDTO result = loadService.createLoad(loadDTO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertNull(result.getFacility());
        assertEquals("SHIP123", result.getShipperId());
        assertEquals("Electronics", result.getProductType());
        verify(loadRepository, times(1)).saveAndFlush(any(Load.class));
    }

    @Test
    void testCreateLoad_NullStatus() {
        // Arrange
        loadDTO.setStatus(null);
        load.setStatus(null);
        when(loadRepository.saveAndFlush(any(Load.class))).thenReturn(load);

        // Act
        LoadDTO result = loadService.createLoad(loadDTO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertNull(result.getStatus());
        assertEquals("SHIP123", result.getShipperId());
        verify(loadRepository, times(1)).saveAndFlush(any(Load.class));
    }

    @Test
    void testCreateLoad_InvalidStatus() {
        // Arrange
        loadDTO.setStatus("INVALID");

        // act & Assert
        assertThrows(IllegalArgumentException.class, () -> loadService.createLoad(loadDTO));
        verify(loadRepository, never()).saveAndFlush(any(Load.class)); 
    }
}
