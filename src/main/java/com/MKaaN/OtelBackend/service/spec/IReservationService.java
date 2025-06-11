package com.MKaaN.OtelBackend.service.spec;

import java.math.BigDecimal;
import java.util.List;

import com.MKaaN.OtelBackend.dto.request.PriceCalculationRequest;
import com.MKaaN.OtelBackend.dto.request.ReservationCreateRequest;
import com.MKaaN.OtelBackend.dto.request.ReservationUpdateRequest;
import com.MKaaN.OtelBackend.dto.projection.ReservationSummary;
import com.MKaaN.OtelBackend.dto.response.ReservationResponse;
import com.MKaaN.OtelBackend.enums.ReservationStatus;

public interface IReservationService {
    // Temel CRUD işlemleri
    ReservationResponse createReservation(ReservationCreateRequest reservationRequest);
    ReservationResponse updateReservation(String id, ReservationUpdateRequest updateRequest);
    void deleteReservation(String id);
    ReservationResponse getReservationById(String id);
    List<ReservationResponse> getAllReservations();

    // Filtreleme işlemleri
    List<ReservationResponse> getReservationsByUser(String email);
    List<ReservationResponse> getReservationsByRoomId(String roomId);
    List<ReservationResponse> getReservationsByStatus(ReservationStatus status);

    // Özet bilgi işlemleri
    ReservationSummary getReservationSummaryById(String id);
    List<ReservationSummary> getReservationSummariesByUserId(String userId);
    List<ReservationSummary> getReservationSummariesByRoomId(String roomId);
    List<ReservationSummary> getReservationSummariesByStatus(ReservationStatus status);

    // Durum güncelleme işlemleri
    ReservationResponse updateReservationStatus(String id, ReservationStatus status);
    void cancelReservation(String id, String email);

    // Fiyat hesaplama ve fatura işlemleri
    BigDecimal calculatePrice(PriceCalculationRequest request);
    byte[] markReservationAsPaid(String id);
    byte[] getInvoicePdf(String id);

    // Yardımcı metodlar
    boolean isReservationOwner(String reservationId, String email);
}
