package com.MKaaN.OtelBackend.service.room;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MKaaN.OtelBackend.dto.RoomDTO;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.exception.ResourceNotFoundException;
import com.MKaaN.OtelBackend.mapper.RoomMapper;
import com.MKaaN.OtelBackend.repository.RoomRepository;

@Service
@Transactional
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) { //Mapstruct uygulaması ile RoomDTO'dan Room entity'sine dönüşüm yapılacak, Room Request Dto ve Room Response Dto arasındaki dönüşüm işlemleri burada yapılacak
        Room room = RoomMapper.toEntity(roomDTO);
        Room savedRoom = roomRepository.save(room);
        return RoomMapper.toDTO(savedRoom);
    }

    @Override
    public RoomDTO getById(String id) {
        Room room = findRoomById(id);
        return RoomMapper.toDTO(room);
    }

    @Override
    public List<RoomDTO> getAll() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toDTO)
                .toList();
    }

    //TODO: RoomDTO'dan Room entity'sine dönüşüm işlemleri Mapstruct uygulaması ile yapılacak MappingTarget kullanılarak güncelleme işlemi yapılacak
    @Override
    public RoomDTO update(String id, RoomDTO roomDTO) {
        Room existingRoom = findRoomById(id);
        RoomMapper.updateEntityFromDTO(roomDTO, existingRoom);
        Room updatedRoom = roomRepository.save(existingRoom);
        return RoomMapper.toDTO(updatedRoom);
    }

    @Override
    public void delete(String id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDTO> getAvailable() {
        return roomRepository.findByAvailableTrue().stream()
                .map(RoomMapper::toDTO)
                .toList();
    }

    private Room findRoomById(String id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }
}