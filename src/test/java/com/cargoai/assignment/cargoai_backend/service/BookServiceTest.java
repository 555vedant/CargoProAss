package com.cargoai.assignment.cargoai_backend.service;

import com.cargoai.assignment.cargoai_backend.dto.BookingDTO;
import com.cargoai.assignment.cargoai_backend.entity.Booking;
import com.cargoai.assignment.cargoai_backend.entity.Load;
import com.cargoai.assignment.cargoai_backend.exception.ResourceNotFoundException;
import com.cargoai.assignment.cargoai_backend.repository.BookingRepository;
import com.cargoai.assignment.cargoai_backend.repository.LoadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private LoadRepository loadRepository;

    @InjectMocks
    private BookingService bookingService;

    private BookingDTO bookingDTO;
    private Booking booking;
    private Load load;
    private UUID id;
    private UUID loadId;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        loadId = UUID.randomUUID();

        // Setup Load entity
        load = new Load();
        load.setId(loadId);
        load.setShipperId("SHIP123");

        // Setup BookingDTO
        bookingDTO = new BookingDTO();
        bookingDTO.setLoadId(loadId);
        bookingDTO.setTransporterId("TRANS123");
        bookingDTO.setProposedRate(1000.50);
        bookingDTO.setComment("Urgent delivery");
        bookingDTO.setStatus("PENDING");
        bookingDTO.setRequestedAt(LocalDateTime.of(2025, 7, 30, 15, 30));

        // Setup Booking entity
        booking = new Booking();
        booking.setId(id);
        booking.setLoad(load);
        booking.setTransporterId("TRANS123");
        booking.setProposedRate(1000.50);
        booking.setComment("Urgent delivery");
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setRequestedAt(LocalDateTime.of(2025, 7, 30, 15, 30));
    }

    @Test
    void testCreateBooking_Success() {
        // Arrange
        when(loadRepository.findById(loadId)).thenReturn(Optional.of(load));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        BookingDTO result = bookingService.createBooking(bookingDTO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(loadId, result.getLoadId());
        assertEquals("TRANS123", result.getTransporterId());
        assertEquals(1000.50, result.getProposedRate());
        assertEquals("Urgent delivery", result.getComment());
        assertEquals("PENDING", result.getStatus());
        assertEquals(LocalDateTime.of(2025, 7, 30, 15, 30), result.getRequestedAt());
        verify(loadRepository, times(1)).findById(loadId);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_NullComment() {
        // Arrange
        bookingDTO.setComment(null);
        booking.setComment(null);
        when(loadRepository.findById(loadId)).thenReturn(Optional.of(load));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        BookingDTO result = bookingService.createBooking(bookingDTO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(loadId, result.getLoadId());
        assertEquals("TRANS123", result.getTransporterId());
        assertEquals(1000.50, result.getProposedRate());
        assertNull(result.getComment());
        assertEquals("PENDING", result.getStatus());
        assertEquals(LocalDateTime.of(2025, 7, 30, 15, 30), result.getRequestedAt());
        verify(loadRepository, times(1)).findById(loadId);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_InvalidStatus() {
        // Arrange
        bookingDTO.setStatus("INVALID");
        when(loadRepository.findById(loadId)).thenReturn(Optional.of(load));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(bookingDTO));
        verify(loadRepository, times(1)).findById(loadId);
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_LoadNotFound() {
        // Arrange
        when(loadRepository.findById(loadId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookingService.createBooking(bookingDTO));
        verify(loadRepository, times(1)).findById(loadId);
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}