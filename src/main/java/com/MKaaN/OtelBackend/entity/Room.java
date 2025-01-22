package com.MKaaN.OtelBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Entity
public class Room {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private Integer guestCount;
    private Double price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;  // Başlangıç tarihi

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;    // Bitiş tarihi

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}