package com.MKaaN.OtelBackend.service.room;

import com.MKaaN.OtelBackend.dto.response.RoomResponse;
import com.MKaaN.OtelBackend.dto.request.RoomCreateRequest;
import com.MKaaN.OtelBackend.dto.request.RoomUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.RoomFilterResponse;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.exception.RoomNotFoundException;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import com.MKaaN.OtelBackend.service.spec.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;

    @Override
    public RoomResponse createRoom(RoomCreateRequest request) {
        roomValidator.validateCreateRequest(request);
        Room room = new Room();
        room.setNumber(request.getNumber());
        room.setType(request.getType());
        room.setPrice(request.getPrice());
        room.setDescription(request.getDescription());
        room.setCapacity(request.getCapacity());
        room.setAvailable(true);
        return convertToResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse updateRoom(String id, RoomUpdateRequest request) {
        roomValidator.validateUpdateRequest(id, request);
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
        room.setNumber(request.getNumber());
        room.setType(request.getType());
        room.setPrice(request.getPrice());
        room.setAvailable(request.isAvailable());
        room.setDescription(request.getDescription());
        room.setCapacity(request.getCapacity());
        return convertToResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(String id) {
        roomValidator.validateDeleteRequest(id);
        roomRepository.deleteById(id);
    }

    @Override
    public RoomResponse getRoomById(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
        return convertToResponse(room);
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomFilterResponse> getAvailableRooms() {
        return roomRepository.findByAvailableTrue().stream()
                .map(this::convertToFilterResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomFilterResponse> getFilteredRooms() {
        return roomRepository.findByAvailableTrue().stream()
                .map(this::convertToFilterResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomFilterResponse getFilteredRoomById(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
        return convertToFilterResponse(room);
    }

    @Override
    public boolean isRoomAvailable(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));
        return room.isAvailable();
    }

    private RoomResponse convertToResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setNumber(room.getNumber());
        response.setType(room.getType());
        response.setPrice(room.getPrice());
        response.setAvailable(room.isAvailable());
        response.setDescription(room.getDescription());
        response.setCapacity(room.getCapacity());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());
        return response;
    }

    private RoomFilterResponse convertToFilterResponse(Room room) {
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
