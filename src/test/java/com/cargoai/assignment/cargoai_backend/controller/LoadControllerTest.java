package com.cargoai.assignment.cargoai_backend.controller;

import com.cargoai.assignment.cargoai_backend.dto.LoadDTO;
import com.cargoai.assignment.cargoai_backend.service.LoadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LoadController.class)
public class LoadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private LoadService loadService;

    @InjectMocks
    private LoadController loadController;

    @Test
    void testCreateLoad_Success() throws Exception {
        LoadDTO loadDTO = createSampleLoadDTO();
        when(loadService.createLoad(any(LoadDTO.class))).thenReturn(loadDTO);

        mockMvc.perform(post("/api/loads")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loadDTO.getId().toString()))
                .andExpect(jsonPath("$.shipperId").value(loadDTO.getShipperId()));
    }

    @Test
    void testCreateLoad_InvalidInput() throws Exception {
        LoadDTO invalidLoadDTO = new LoadDTO(); // Missing required fields
        mockMvc.perform(post("/api/loads")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLoadDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.shipperId").value("must not be blank"));
    }

    @Test
    void testGetAllLoads_Success() throws Exception {
        LoadDTO loadDTO = createSampleLoadDTO();
        Page<LoadDTO> page = new PageImpl<>(Collections.singletonList(loadDTO));
        when(loadService.getAllLoads(any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/loads")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(loadDTO.getId().toString()))
                .andExpect(jsonPath("$.content[0].shipperId").value(loadDTO.getShipperId()));
    }

    @Test
    void testGetLoadById_Success() throws Exception {
        LoadDTO loadDTO = createSampleLoadDTO();
        when(loadService.getLoad(any(UUID.class))).thenReturn(loadDTO);

        mockMvc.perform(get("/api/loads/{id}", loadDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loadDTO.getId().toString()))
                .andExpect(jsonPath("$.shipperId").value(loadDTO.getShipperId()));
    }

    private LoadDTO createSampleLoadDTO() {
        
       
        LoadDTO loadDTO = new LoadDTO();
        loadDTO.setId(UUID.randomUUID());
        loadDTO.setShipperId("shipper-123");
        LoadDTO.FacilityDTO facilityDTO = new LoadDTO.FacilityDTO();
        facilityDTO.setLoadingPoint("Loading Point A");
        facilityDTO.setUnloadingPoint("Unloading Point B");
        facilityDTO.setLoadingDate(LocalDateTime.now());
        facilityDTO.setUnloadingDate(LocalDateTime.now().plusDays(1));
        loadDTO.setFacility(facilityDTO);
        loadDTO.setProductType("Electronics");
        loadDTO.setTruckType("Flatbed");
        loadDTO.setNoOfTrucks(2);
        loadDTO.setWeight(1000.0);
        loadDTO.setComment("Handle with care");
        loadDTO.setDatePosted(LocalDateTime.now());
        loadDTO.setStatus("PENDING");
        
        return loadDTO;
    }
}