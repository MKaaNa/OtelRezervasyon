package com.MKaaN.OtelBackend.service.room;

import java.util.List;

import com.MKaaN.OtelBackend.dto.RoomDTO;

public interface IRoomService {
    RoomDTO create(RoomDTO roomDTO);
    RoomDTO getById(String id); //Roomrequest Dto ve Room response Dto arasındaki dönüşüm işlemleri burada yapılacak
    List<RoomDTO> getAll();
    RoomDTO update(String id, RoomDTO roomDTO);
    void delete(String id);
    List<RoomDTO> getAvailable();
} 