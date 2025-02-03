package com.MKaaN.OtelBackend.service;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}