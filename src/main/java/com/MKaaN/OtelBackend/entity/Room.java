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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;      // "single", "double", "suite" gibi değerler alır.
    private Integer guestCount;
    private Double price;         // Günlük fiyat

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;  // Odaların müsaitlik başlangıç tarihi

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;    // Odaların müsaitlik bitiş tarihi

    // Getters ve Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
        // Oda tipine göre varsayılan fiyat ataması
        if (roomType != null) {
            switch (roomType.toLowerCase()) {
                case "single":
                    this.price = 350.0;
                    break;
                case "double":
                    this.price = 450.0;
                    break;
                case "suite":
                    this.price = 800.0;
                    break;
                default:
                    this.price = 0.0; // Bilinmeyen oda tipi için
            }
        }
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

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getRoomType() {
        return roomType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}