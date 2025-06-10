package com.MKaaN.OtelBackend.service;

import java.math.BigDecimal;
import java.util.List;

import com.MKaaN.OtelBackend.dto.PriceCalculationRequest;
import com.MKaaN.OtelBackend.dto.ReservationDTO;
import com.MKaaN.OtelBackend.dto.projection.ReservationSummary;
import com.MKaaN.OtelBackend.enums.ReservationStatus;

public interface IReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO updateReservation(String id, ReservationDTO reservationDTO);
    void deleteReservation(String id);
    ReservationDTO getReservationById(String id);
    List<ReservationDTO> getAllReservations();
    List<ReservationDTO> getReservationsByUserId(String userId);
    List<ReservationDTO> getReservationsByRoomId(String roomId);
    BigDecimal calculatePrice(PriceCalculationRequest request);
    ReservationDTO updateReservationStatus(String id, ReservationStatus status);
    List<ReservationDTO> getReservationsByStatus(ReservationStatus status);
    byte[] markReservationAsPaid(String id);
    byte[] getInvoicePdf(String id);

    /**
     * Bir rezervasyonun özet bilgilerini getirir.
     * Sadece ihtiyaç duyulan temel alanları içerir, ilişkili entity'lerin tüm verilerini çekmez.
     *
     * @param id Rezervasyon ID
     * @return Rezervasyon özeti
     */
    ReservationSummary getReservationSummaryById(String id);

    /**
     * Bir kullanıcıya ait rezervasyonların özet bilgilerini getirir.
     * Sadece ihtiyaç duyulan temel alanları içerir, ilişkili entity'lerin tüm verilerini çekmez.
     *
     * @param userId Kullanıcı ID
     * @return Rezervasyon özetleri listesi
     */
    List<ReservationSummary> getReservationSummariesByUserId(String userId);

    /**
     * Bir odaya ait rezervasyonların özet bilgilerini getirir.
     * Sadece ihtiyaç duyulan temel alanları içerir, ilişkili entity'lerin tüm verilerini çekmez.
     *
     * @param roomId Oda ID
     * @return Rezervasyon özetleri listesi
     */
    List<ReservationSummary> getReservationSummariesByRoomId(String roomId);

    /**
     * Belirli bir durumdaki rezervasyonların özet bilgilerini getirir.
     * Sadece ihtiyaç duyulan temel alanları içerir, ilişkili entity'lerin tüm verilerini çekmez.
     *
     * @param status Rezervasyon durumu
     * @return Rezervasyon özetleri listesi
     */
    List<ReservationSummary> getReservationSummariesByStatus(ReservationStatus status);
}
