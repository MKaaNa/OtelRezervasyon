package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.request.ReservationCreateRequest;
import com.MKaaN.OtelBackend.dto.request.ReservationUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.ReservationResponse;
import com.MKaaN.OtelBackend.entity.Reservation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    // Request'ten Entity'ye dönüşüm
    Reservation toEntity(ReservationCreateRequest request);

    // Entity'den Response'a dönüşüm
    ReservationResponse toResponse(Reservation reservation);

    // Entity'den Response'a dönüşüm (eski toDTO metodunun yerine)
    ReservationResponse toDTO(Reservation reservation);

    // Update işlemleri için
    void updateReservation(ReservationUpdateRequest request, @MappingTarget Reservation reservation);

    // Response'dan Entity'ye güncelleme
    void updateReservationFromDTO(ReservationResponse response, @MappingTarget Reservation reservation);
}
