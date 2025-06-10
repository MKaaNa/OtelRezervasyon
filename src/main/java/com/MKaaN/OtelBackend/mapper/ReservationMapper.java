package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.ReservationDTO;
import com.MKaaN.OtelBackend.entity.Reservation;

public final class ReservationMapper {
    
    private ReservationMapper() {
        // Private constructor to prevent instantiation
    }
    
    public static ReservationMapper of() {
        return new ReservationMapper();
    }
    
    public static ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        
        return ReservationDTO.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom() != null ? reservation.getRoom().getId() : null)
                .userId(reservation.getUser() != null ? reservation.getUser().getId() : null)
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .totalPrice(reservation.getTotalPrice())
                .adminNote(reservation.getAdminNote())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .build();
    }

    public static Reservation toEntity(ReservationDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Reservation.builder()
                .id(dto.getId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .totalPrice(dto.getTotalPrice())
                .adminNote(dto.getAdminNote())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static void updateEntityFromDTO(Reservation reservation, ReservationDTO dto) {
        if (reservation == null || dto == null) {
            return;
        }
        
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        reservation.setStatus(dto.getStatus());
        reservation.setTotalPrice(dto.getTotalPrice());
        reservation.setAdminNote(dto.getAdminNote());
        reservation.setCreatedAt(dto.getCreatedAt());
        reservation.setUpdatedAt(dto.getUpdatedAt());
    }
} 