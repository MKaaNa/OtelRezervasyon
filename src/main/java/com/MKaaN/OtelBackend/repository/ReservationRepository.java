package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatus(ReservationStatus status);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reservation r WHERE r.room.id = :roomId AND r.endDate > :startDate AND r.startDate < :endDate")
    boolean existsByRoomIdAndDateRange(@Param("roomId") Long roomId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}