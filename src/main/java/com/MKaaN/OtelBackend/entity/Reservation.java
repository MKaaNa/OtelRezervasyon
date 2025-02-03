package com.MKaaN.OtelBackend.entity;

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

    // Başlangıç ve bitiş tarihleri
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    // Misafir sayısı
    private int guestCount;

    // Toplam fiyat; DB tarafında null olamaz.
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    /**
     * Toplam fiyatı hesaplar.
     * Örneğin: (konaklama süresi (gün) * oda fiyatı)
     */
    public void calculateTotalPrice() {
        if (room != null && startDate != null && endDate != null) {
            // İki tarih arasındaki farkı milisaniye cinsinden hesaplayalım
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            // Gün cinsinden farkı hesapla
            long diffDays = diffInMillies / (24 * 60 * 60 * 1000);
            // Eğer rezervasyon süresi 0 gün olarak gelirse (aynı gün için rezervasyon gibi) en az 1 gün olarak kabul edelim
            diffDays = diffDays == 0 ? 1 : diffDays;
            // Room entity'sinde tanımlı fiyat bilgisini (örneğin, price) kullanarak hesaplama yapıyoruz
            this.totalPrice = room.getPrice() * diffDays;
        }
    }

    /**
     * Entity kaydedilmeden hemen önce toplam fiyatın null olup olmadığını kontrol edip,
     * eğer null ise hesaplamayı tetikler.
     */
    @PrePersist
    public void prePersist() {
        if (this.totalPrice == null) {
            calculateTotalPrice();
            // Hala null ise (örneğin; room ya da tarih bilgileri eksikse) sıfır değer atayabilir veya hata fırlatabilirsiniz.
            if (this.totalPrice == null) {
                this.totalPrice = 0.0;
            }
        }
    }

    // Getters and Setters

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
}