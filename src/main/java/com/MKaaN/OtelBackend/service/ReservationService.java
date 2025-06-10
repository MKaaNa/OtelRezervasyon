package com.MKaaN.OtelBackend.service;

import java.math.BigDecimal;
import java.util.List;

import com.MKaaN.OtelBackend.dto.PriceCalculationRequest;
import com.MKaaN.OtelBackend.dto.ReservationDTO;
import com.MKaaN.OtelBackend.enums.ReservationStatus;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO updateReservation(String id, ReservationDTO reservationDTO);
    void deleteReservation(String id);
    ReservationDTO getReservationById(String id);
    List<ReservationDTO> getAllReservations();
    List<ReservationDTO> getReservationsByUserId(String userId);
    List<ReservationDTO> getReservationsByRoomId(String roomId);
    BigDecimal calculatePrice(PriceCalculationRequest request);
    ReservationDTO updateReservationStatus(String id, String status);
    List<ReservationDTO> getReservationsByStatus(ReservationStatus status);
    byte[] markReservationAsPaid(String id);
    byte[] getInvoicePdf(String id);
} 