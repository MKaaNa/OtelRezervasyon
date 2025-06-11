package com.MKaaN.OtelBackend.service.spec;

import java.util.List;
import com.MKaaN.OtelBackend.dto.response.RoomResponse;
import com.MKaaN.OtelBackend.dto.response.RoomFilterResponse;
import com.MKaaN.OtelBackend.dto.request.RoomCreateRequest;
import com.MKaaN.OtelBackend.dto.request.RoomUpdateRequest;

public interface IRoomService {
    // Temel CRUD işlemleri
    RoomResponse createRoom(RoomCreateRequest request);
    RoomResponse getRoomById(String id);
    List<RoomResponse> getAllRooms();
    RoomResponse updateRoom(String id, RoomUpdateRequest request);
    void deleteRoom(String id);

    // Filtreleme işlemleri
    List<RoomFilterResponse> getAvailableRooms();
    List<RoomFilterResponse> getFilteredRooms();
    RoomFilterResponse getFilteredRoomById(String id);

    // Yardımcı metodlar
    boolean isRoomAvailable(String roomId);
}
