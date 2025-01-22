package com.MKaaN.OtelBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private Long userId;  // Kullanıcı ID (foreign key)

    @Getter @Setter
    private Long roomId;  // Oda ID (foreign key)

    @Getter @Setter
    private Date startDate;  // Konaklama başlangıç tarihi

    @Getter @Setter
    private Date endDate;  // Konaklama bitiş tarihi

    @Getter @Setter
    private int numberOfPeople;  // Konaklayacak kişi sayısı
}