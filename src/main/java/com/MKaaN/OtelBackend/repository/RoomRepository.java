package com.MKaaN.OtelBackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.enums.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findByRoomTypeAndGuestCount(RoomType roomType, Integer guestCount);
    long countByRoomTypeAndGuestCount(RoomType roomType, Integer guestCount);
    
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.guestCount = :guestCount " +
           "AND r.id NOT IN (SELECT res.room.id FROM Reservation res " +
           "WHERE res.status = 'CONFIRMED' AND " +
           "((res.startDate <= :endDate AND res.endDate >= :startDate)))")
    List<Room> findAvailable(@Param("roomType") RoomType roomType,
                           @Param("guestCount") Integer guestCount,
                           @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate);
    @Query("SELECT r FROM Room r " +
           "WHERE (:roomType IS NULL OR r.roomType = :roomType) " +
           "AND (:guestCount IS NULL OR r.guestCount = :guestCount) " +
           "AND r.available = true")
    List<Room> findAvailable(@Param("roomType") RoomType roomType,
                           @Param("guestCount") Integer guestCount);

    List<Room> findByAvailableTrue(); //Projection için kullanılabilir
} 