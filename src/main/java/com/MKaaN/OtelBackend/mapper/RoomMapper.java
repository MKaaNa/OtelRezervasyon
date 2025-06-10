package com.MKaaN.OtelBackend.mapper;

import java.time.LocalDateTime;

import com.MKaaN.OtelBackend.dto.RoomDTO;
import com.MKaaN.OtelBackend.entity.Room;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomMapper {



    public static RoomDTO toDTO(Room room) {
        if (room == null) {
            return null;
        }

        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .price(room.getPrice())
                .available(room.isAvailable())
                .guestCount(room.getGuestCount())
                .description(room.getDescription())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }

    public static Room toEntity(RoomDTO dto) {
        if (dto == null) {
            return null;
        }

        Room room = Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(dto.getRoomType())
                .price(dto.getPrice())
                .available(dto.isAvailable())
                .guestCount(dto.getGuestCount())
                .description(dto.getDescription())
                .build();

        if (dto.getId() != null) {
            room.setId(dto.getId());
        }

        // Set creation and update times
        LocalDateTime now = LocalDateTime.now();
        if (dto.getCreatedAt() != null) {
            room.setCreatedAt(dto.getCreatedAt());
        } else {
            room.setCreatedAt(now);
        }

        room.setUpdatedAt(now);

        return room;
    }

    public static void updateEntityFromDTO(RoomDTO dto, Room room) {
        if (room == null || dto == null) {
            return;
        }

        if (dto.getRoomNumber() != null) {
            room.setRoomNumber(dto.getRoomNumber());
        }

        if (dto.getRoomType() != null) {
            room.setRoomType(dto.getRoomType());
        }

        if (dto.getPrice() != null) {
            room.setPrice(dto.getPrice());
        }

        room.setAvailable(dto.isAvailable());

        if (dto.getGuestCount() != null) {
            room.setGuestCount(dto.getGuestCount());
        }

        if (dto.getDescription() != null) {
            room.setDescription(dto.getDescription());
        }

        room.setUpdatedAt(LocalDateTime.now());
    }
}
