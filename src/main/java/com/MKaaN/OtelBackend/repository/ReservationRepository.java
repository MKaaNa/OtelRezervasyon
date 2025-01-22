package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Rezervasyonla ilgili işlemler yapılabilir
}