package com.MKaaN.OtelBackend.entity;

import com.MKaaN.OtelBackend.enums.ReservationStatus;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kullanıcı bilgisi
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Oda bilgisi
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private int guestCount;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;  // PENDING, APPROVED, REJECTED, PAID

    private String adminNote; 

    // Toplam fiyatı hesaplar: (konaklama süresi (gün) * oda günlük fiyatı)
    public void calculateTotalPrice() {
        if (room != null && startDate != null && endDate != null) {
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long diffDays = diffInMillies / (24 * 60 * 60 * 1000);
            diffDays = diffDays == 0 ? 1 : diffDays;
            this.totalPrice = room.getPrice() * diffDays;
        }
    }

    @PrePersist
    public void prePersist() {
        if (this.totalPrice == null) {
            calculateTotalPrice();
            if (this.totalPrice == null) {
                this.totalPrice = 0.0;
            }
        }
        if (this.status == null) {
            this.status = ReservationStatus.PENDING;
        }
    }

    // Getters and Setters
    // (oluşturduğunuz mevcut getter/setter’ları bu alana ekleyin)

    // Örnek getter ve setter:
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public int getGuestCount() {
        return guestCount;
    }
    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public ReservationStatus getStatus() {
        return status;
    }
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
    public String getAdminNote() {
        return adminNote;
    }
    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}