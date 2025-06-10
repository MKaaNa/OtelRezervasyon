package com.MKaaN.OtelBackend.controller;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MKaaN.OtelBackend.dto.ApiResponse;
import com.MKaaN.OtelBackend.dto.PriceCalculationRequest;
import com.MKaaN.OtelBackend.dto.ReservationDTO;
import com.MKaaN.OtelBackend.dto.projection.ReservationSummary;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.service.IReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;


    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<ReservationDTO> add(@Valid @RequestBody ReservationDTO reservationDTO) {
        return ApiResponse.success(reservationService.createReservation(reservationDTO));
    }

    @PostMapping("/calculate-price")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<BigDecimal> calcPrice(@Valid @RequestBody PriceCalculationRequest request) {
        return ApiResponse.success(reservationService.calculatePrice(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ReservationDTO>> getAll() {
        return ApiResponse.success(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<ReservationDTO> get(@PathVariable String id) {
        return ApiResponse.success(reservationService.getReservationById(id));
    }

    @GetMapping("/{id}/summary")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<ReservationSummary> getSummary(@PathVariable String id) {
        return ApiResponse.success(reservationService.getReservationSummaryById(id));
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<ReservationDTO>> getByStatus(@PathVariable String status) {
        ReservationStatus reservationStatus = ReservationStatus.valueOf(status);
        return ApiResponse.success(reservationService.getReservationsByStatus(reservationStatus));
    }

    @GetMapping("/status/{status}/summary")
    public ApiResponse<List<ReservationSummary>> getSummaryByStatus(@PathVariable String status) {
        ReservationStatus reservationStatus = ReservationStatus.valueOf(status);
        return ApiResponse.success(reservationService.getReservationSummariesByStatus(reservationStatus));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<ReservationDTO> edit(@PathVariable String id, @Valid @RequestBody ReservationDTO reservationDTO) {
        return ApiResponse.success(reservationService.updateReservation(id, reservationDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<Void> del(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}/pay")
    public ApiResponse<byte[]> markAsPaid(@PathVariable String id) {
        return ApiResponse.success(reservationService.markReservationAsPaid(id));
    }

    @GetMapping("/{id}/invoice")
    public ApiResponse<byte[]> getInvoice(@PathVariable String id) {
        return ApiResponse.success(reservationService.getInvoicePdf(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<List<ReservationDTO>> getByUserId(@PathVariable String userId) {
        return ApiResponse.success(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/user/{userId}/summary")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ApiResponse<List<ReservationSummary>> getSummaryByUserId(@PathVariable String userId) {
        return ApiResponse.success(reservationService.getReservationSummariesByUserId(userId));
    }

    @GetMapping("/room/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ReservationDTO>> getByRoomId(@PathVariable String roomId) {
        return ApiResponse.success(reservationService.getReservationsByRoomId(roomId));
    }

    @GetMapping("/room/{roomId}/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ReservationSummary>> getSummaryByRoomId(@PathVariable String roomId) {
        return ApiResponse.success(reservationService.getReservationSummariesByRoomId(roomId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ReservationDTO> updateStatus(@PathVariable String id, @RequestBody ReservationStatus status) {
        return ApiResponse.success(reservationService.updateReservationStatus(id, status));
    }
}
