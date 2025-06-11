package com.MKaaN.OtelBackend.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.MKaaN.OtelBackend.dto.response.RoomResponse;
import com.MKaaN.OtelBackend.dto.request.RoomCreateRequest;
import com.MKaaN.OtelBackend.dto.request.RoomUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.RoomFilterResponse;
import com.MKaaN.OtelBackend.entity.Room;

@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomResponse toResponse(Room room) {
        if (room == null) {
            return null;
        }

        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setNumber(room.getNumber());
        roomResponse.setType(room.getType());
        roomResponse.setPrice(room.getPrice());
        roomResponse.setAvailable(room.isAvailable());
        roomResponse.setDescription(room.getDescription());
        roomResponse.setCapacity(room.getCapacity());
        roomResponse.setCreatedAt(room.getCreatedAt());
        roomResponse.setUpdatedAt(room.getUpdatedAt());

        return roomResponse;
    }

    @Override
    public Room toEntity(RoomResponse roomResponse) {
        if (roomResponse == null) {
            return null;
        }

        Room room = new Room();
        room.setId(roomResponse.getId());
        room.setNumber(roomResponse.getNumber());
        room.setType(roomResponse.getType());
        room.setPrice(roomResponse.getPrice());
        room.setAvailable(roomResponse.isAvailable());
        room.setDescription(roomResponse.getDescription());
        room.setCapacity(roomResponse.getCapacity());
        room.setCreatedAt(roomResponse.getCreatedAt());
        room.setUpdatedAt(roomResponse.getUpdatedAt());

        return room;
    }

    @Override
    public Room toEntity(RoomCreateRequest request) {
        if (request == null) {
            return null;
        }

        Room room = new Room();
        room.setNumber(request.getNumber());
        room.setType(request.getType());
        room.setPrice(request.getPrice());
        room.setAvailable(true);
        room.setDescription(request.getDescription());
        room.setCapacity(request.getCapacity());

        return room;
    }

    @Override
    public Room toEntity(RoomUpdateRequest request) {
        if (request == null) {
            return null;
        }

        Room room = new Room();
        room.setId(request.getId());
        room.setNumber(request.getNumber());
        room.setType(request.getType());
        room.setPrice(request.getPrice());
        room.setAvailable(request.isAvailable());
        room.setDescription(request.getDescription());
        room.setCapacity(request.getCapacity());

        return room;
    }

    @Override
    public RoomFilterResponse toFilterResponse(Room room) {
        if (room == null) {
            return null;
        }

        RoomFilterResponse response = new RoomFilterResponse();
        response.setId(room.getId());
        response.setNumber(room.getNumber());
        response.setType(room.getType());
        response.setPrice(room.getPrice());
        response.setAvailable(room.isAvailable());
        response.setDescription(room.getDescription());
        response.setCapacity(room.getCapacity());

        return response;
    }
}