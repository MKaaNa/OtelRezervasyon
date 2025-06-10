package com.MKaaN.OtelBackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.MKaaN.OtelBackend.dto.projection.ReservationSummary;
import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.enums.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    
    // İhtiyaç duyulan DTO projectionları için sorguları ekleyelim

    /**
     * Belirli bir ID'ye sahip rezervasyonu özet bilgilerle getirir
     */
    @Query("SELECT r.id as id, r.startDate as startDate, r.endDate as endDate, " +
           "r.status as status, r.totalPrice as totalPrice, r.createdAt as createdAt, " +
           "r.room.id as roomId, r.user.id as userId " +
           "FROM Reservation r WHERE r.id = :id")
    Optional<ReservationSummary> findSummaryById(@Param("id") String id);

    /**
     * Kullanıcıya ait rezervasyonları özet bilgilerle getirir
     */
    @Query("SELECT r.id as id, r.startDate as startDate, r.endDate as endDate, " +
           "r.status as status, r.totalPrice as totalPrice, r.createdAt as createdAt, " +
           "r.room.id as roomId, r.user.id as userId " +
           "FROM Reservation r WHERE r.user.id = :userId")
    List<ReservationSummary> findSummariesByUserId(@Param("userId") String userId);

    /**
     * Odaya ait rezervasyonları özet bilgilerle getirir
     */
    @Query("SELECT r.id as id, r.startDate as startDate, r.endDate as endDate, " +
           "r.status as status, r.totalPrice as totalPrice, r.createdAt as createdAt, " +
           "r.room.id as roomId, r.user.id as userId " +
           "FROM Reservation r WHERE r.room.id = :roomId")
    List<ReservationSummary> findSummariesByRoomId(@Param("roomId") String roomId);

    /**
     * Belirtilen duruma göre rezervasyonları özet bilgilerle getirir
     */
    @Query("SELECT r.id as id, r.startDate as startDate, r.endDate as endDate, " +
           "r.status as status, r.totalPrice as totalPrice, r.createdAt as createdAt, " +
           "r.room.id as roomId, r.user.id as userId " +
           "FROM Reservation r WHERE r.status = :status")
    List<ReservationSummary> findSummariesByStatus(@Param("status") ReservationStatus status);

    // Mevcut sorgular aynen korunuyor

    /**
     * Belirtilen duruma göre rezervasyonları bulur
     */
    List<Reservation> findByStatus(ReservationStatus status);

    /**
     * Belirli bir kullanıcıya ait rezervasyonları bulur
     */
    List<Reservation> findByUserId(String userId);

    /**
     * Belirli bir odaya ait rezervasyonları bulur
     */
    List<Reservation> findByRoomId(String roomId);
    
    /**
     * Belirli bir oda için belirtilen tarih aralığında rezervasyon olup olmadığını kontrol eder
     */
    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
           "WHERE r.room.id = :roomId " +
           "AND r.status = 'CONFIRMED' " +
           "AND ((r.startDate <= :endDate AND r.endDate >= :startDate))")
    boolean existsByRoomIdAndDateRange(@Param("roomId") String roomId,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    /**
     * Belirli bir oda için belirtilen tarih aralığında, belirtilen ID dışında rezervasyon olup olmadığını kontrol eder
     */
    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
           "WHERE r.room.id = :roomId " +
           "AND r.id != :reservationId " +
           "AND r.status = 'CONFIRMED' " +
           "AND ((r.startDate <= :endDate AND r.endDate >= :startDate))")
    boolean existsByRoomIdAndDateRangeExcludingId(@Param("roomId") String roomId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate,
                                                @Param("reservationId") String reservationId);

    /**
     * Belirli bir tarihte başlayacak olan onaylanmış rezervasyonları bulur
     */
    @Query("SELECT r FROM Reservation r " +
           "WHERE r.status = 'CONFIRMED' " +
           "AND r.startDate = :date")
    List<Reservation> findUpcomingReservations(@Param("date") LocalDate date);

    /**
     * Belirli bir oda için belirtilen tarih aralığındaki rezervasyonları bulur
     */
    @Query("SELECT r FROM Reservation r " +
           "WHERE r.room.id = :roomId " +
           "AND ((r.startDate <= :endDate AND r.endDate >= :startDate))")
    List<Reservation> findByRoomIdAndDateRange(@Param("roomId") String roomId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND " +
           "((r.startDate <= :endDate AND r.endDate >= :startDate) OR " +
           "(r.startDate >= :startDate AND r.startDate <= :endDate) OR " +
           "(r.endDate >= :startDate AND r.endDate <= :endDate))")
    List<Reservation> findOverlappingReservations(
            @Param("roomId") String roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}

