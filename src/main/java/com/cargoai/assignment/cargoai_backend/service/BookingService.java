package com.cargoai.assignment.cargoai_backend.service;

import com.cargoai.assignment.cargoai_backend.dto.BookingDTO;
import com.cargoai.assignment.cargoai_backend.entity.Booking;
import com.cargoai.assignment.cargoai_backend.entity.Load;
import com.cargoai.assignment.cargoai_backend.repository.BookingRepository;
import com.cargoai.assignment.cargoai_backend.repository.LoadRepository;
import com.cargoai.assignment.cargoai_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LoadRepository loadRepository;

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Load load = loadRepository.findById(bookingDTO.getLoadId())
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + bookingDTO.getLoadId()));
        Booking booking = convertToEntity(bookingDTO);
        booking.setLoad(load);
        booking = bookingRepository.save(booking);
        return convertToDTO(booking);
    }

    public BookingDTO updateBooking(UUID id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        updateEntityFromDTO(booking, bookingDTO);
        booking = bookingRepository.save(booking);
        return convertToDTO(booking);
    }

    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }

    public BookingDTO getBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        return convertToDTO(booking);
    }

    public Page<BookingDTO> getAllBookings(Pageable pageable, String transporterId) {
        Page<Booking> bookings = transporterId != null
                ? bookingRepository.findByTransporterId(transporterId, pageable)
                : bookingRepository.findAll(pageable);
        return bookings.map(this::convertToDTO);
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setLoadId(booking.getLoad().getId());
        bookingDTO.setTransporterId(booking.getTransporterId());
        bookingDTO.setProposedRate(booking.getProposedRate());
        bookingDTO.setComment(booking.getComment());
        bookingDTO.setStatus(booking.getStatus().name());
        bookingDTO.setRequestedAt(booking.getRequestedAt());
        return bookingDTO;
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setTransporterId(bookingDTO.getTransporterId());
        booking.setProposedRate(bookingDTO.getProposedRate());
        booking.setComment(bookingDTO.getComment());
        booking.setStatus(Booking.BookingStatus.valueOf(bookingDTO.getStatus()));
        booking.setRequestedAt(bookingDTO.getRequestedAt());
        return booking;
    }

    private void updateEntityFromDTO(Booking booking, BookingDTO bookingDTO) {
        booking.setTransporterId(bookingDTO.getTransporterId());
        booking.setProposedRate(bookingDTO.getProposedRate());
        booking.setComment(bookingDTO.getComment());
        booking.setStatus(Booking.BookingStatus.valueOf(bookingDTO.getStatus()));
        booking.setRequestedAt(bookingDTO.getRequestedAt());
        if (bookingDTO.getLoadId() != null) {
            Load load = loadRepository.findById(bookingDTO.getLoadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + bookingDTO.getLoadId()));
            booking.setLoad(load);
        }
    }
}