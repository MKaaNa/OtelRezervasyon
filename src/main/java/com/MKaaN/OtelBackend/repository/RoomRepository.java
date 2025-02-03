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

    // Oda tipi, kişi sayısı ve tarih aralığına göre odaları sorgula
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.guestCount = :guestCount AND r.startDate <= :endDate AND r.endDate >= :startDate")
    List<Room> findRoomsByCriteria(
            @Param("roomType") String roomType,
            @Param("guestCount") Integer guestCount,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Oda tipi ve kişi sayısına göre odaları sorgula
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.guestCount = :guestCount")
    List<Room> findRoomsByTypeAndGuests(
            @Param("roomType") String roomType,
            @Param("guestCount") Integer guestCount);
}

