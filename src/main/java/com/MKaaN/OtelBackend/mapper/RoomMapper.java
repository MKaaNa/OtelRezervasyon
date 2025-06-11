package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.response.RoomResponse;
import com.MKaaN.OtelBackend.dto.request.RoomCreateRequest;
import com.MKaaN.OtelBackend.dto.request.RoomUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.RoomFilterResponse;
import com.MKaaN.OtelBackend.entity.Room;

/**
 * Room entity'si ile ilgili dönüşümleri yapan arayüz
 */
public interface RoomMapper {

    /**
     * Room entity'sini RoomResponse'a dönüştürür
     *
     * @param room Room entity
     * @return RoomResponse
     */
    RoomResponse toResponse(Room room);

    /**
     * RoomResponse'u Room entity'sine dönüştürür
     *
     * @param roomResponse RoomResponse
     * @return Room entity
     */
    Room toEntity(RoomResponse roomResponse);

    /**
     * RoomCreateRequest'i Room entity'sine dönüştürür
     *
     * @param request RoomCreateRequest
     * @return Room entity
     */
    Room toEntity(RoomCreateRequest request);

    /**
     * RoomUpdateRequest'i Room entity'sine dönüştürür
     *
     * @param request RoomUpdateRequest
     * @return Room entity
     */
    Room toEntity(RoomUpdateRequest request);

    /**
     * Room entity'sini RoomFilterResponse'a dönüştürür
     *
     * @param room Room entity
     * @return RoomFilterResponse
     */
    RoomFilterResponse toFilterResponse(Room room);
}
