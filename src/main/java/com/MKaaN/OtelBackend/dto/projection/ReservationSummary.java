package com.MKaaN.OtelBackend.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.MKaaN.OtelBackend.enums.ReservationStatus;

/**
 * Rezervasyon bilgilerinin özet görünümü için projection interface
 * Sadece ihtiyaç duyulan alanları içerir
 */
public interface ReservationSummary {
    String getId();
    String getRoomId();
    String getUserId();
    LocalDate getStartDate();
    LocalDate getEndDate();
    ReservationStatus getStatus();
    BigDecimal getTotalPrice();
    LocalDateTime getCreatedAt();

    // Nested projection ile ilişkili entity'lerden sadece gerekli bilgileri çekme
    interface RoomInfo {
        String getId();
        String getRoomNumber();
        BigDecimal getPrice();
    }

    interface UserInfo {
        String getId();
        String getUsername();
        String getEmail();
    }

    // Opsiyonel: ihtiyaca göre ilişkili entity'lerden özet bilgi alabilmek için
    RoomInfo getRoom();
    UserInfo getUser();
}
