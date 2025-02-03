package com.MKaaN.OtelBackend.service;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.MKaaN.OtelBackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class ReservationReminderScheduler {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmailService emailService;

    // Her gün saat 08:00'de çalışacak şekilde
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendReservationReminders() {
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(1);

        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if ((reservation.getStatus() == ReservationStatus.APPROVED || reservation.getStatus() == ReservationStatus.PENDING)
                    && reservation.getStartDate() != null) {
                LocalDate resStart = reservation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (resStart.equals(reminderDate)) {
                    String email = reservation.getUser().getEmail();
                    String subject = "Reservation Reminder";
                    String text = "Dear Customer,\n\nThis is a reminder that your reservation starting on "
                            + resStart.toString() + " is coming up.\n\nThank you.";
                    emailService.sendEmail(email, subject, text);
                }
            }
        }
    }
}