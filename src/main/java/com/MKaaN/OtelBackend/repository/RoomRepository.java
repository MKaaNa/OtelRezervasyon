package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Mevcut sorgu: Oda tipi, kişi sayısı ve tarih aralığına göre odaları sorgula
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.guestCount = :guestCount AND (r.startDate BETWEEN :startDate AND :endDate OR r.endDate BETWEEN :startDate AND :endDate)")
    List<Room> findRoomsByCriteria(
            @Param("roomType") String roomType,
            @Param("guestCount") Integer guestCount,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Yeni sorgu: Sadece oda tipi ve misafir sayısı girildiğinde, bugünden sonraki müsait odaları getir
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.guestCount = :guestCount AND r.startDate >= :today")
    List<Room> findAvailableRoomsAfter(
            @Param("roomType") String roomType,
            @Param("guestCount") Integer guestCount,
            @Param("today") LocalDate today);

    // Sadece oda tipi ve misafir sayısına göre odaları sorgulayan mevcut metot (eski versiyon, gerekirse kullanılabilir)
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.guestCount = :guestCount")
    List<Room> findRoomsByTypeAndGuests(
            @Param("roomType") String roomType,
            @Param("guestCount") Integer guestCount);
}