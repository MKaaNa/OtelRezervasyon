package com.MKaaN.OtelBackend.repository;

import com.MKaaN.OtelBackend.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Oda tipi, kişi sayısı ve tarih aralığına göre odaları al
    public List<Room> getRooms(String roomType, Integer guestCount, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            // Tarihler verilmişse, tarih filtresi ile odaları al
            return roomRepository.findRoomsByCriteria(roomType, guestCount, startDate, endDate);
        } else {
            // Tarihler verilmemişse, sadece oda tipi ve kişi sayısına göre odaları al
            return roomRepository.findRoomsByTypeAndGuests(roomType, guestCount);
        }
    }
    // Oda kaydetme işlemi
    public Room saveRoom(Room room) {
        return roomRepository.save(room);  // Veritabanına kaydet
    }

    // Tüm odaları al
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }
}


