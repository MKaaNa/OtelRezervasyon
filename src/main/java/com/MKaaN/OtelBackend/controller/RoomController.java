package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.repository.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Odanın türüne, kişi sayısına ve tarih aralığına göre filtreli odaları döndür
    @GetMapping
    public ResponseEntity<List<Room>> getRooms(
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) Integer guestCount,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<Room> rooms;

        // Eğer hiçbir filtre verilmemişse, tüm odaları döndür
        if (roomType == null && guestCount == null && startDate == null && endDate == null) {
            rooms = roomService.findAllRooms();  // Tüm odaları al
        } else {
            rooms = roomService.getRooms(roomType, guestCount, startDate, endDate);  // Filtre ile odaları al
        }

        return ResponseEntity.ok(rooms);
    }

    // Oda eklemek için POST metodu
    @PostMapping("")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room createdRoom = roomService.saveRoom(room);  // Odayı veritabanına kaydediyoruz
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);  // 201 Created ile yanıt veriyoruz
    }

    // Odayı güncellemek için PUT metodu
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Room room = roomService.findRoomById(id);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Oda bulunamadığında 404 döndür
        }

        // Güncellenmiş bilgileri odaya uygula
        room.setRoomType(roomDetails.getRoomType());
        room.setPrice(roomDetails.getPrice());
        room.setStartDate(roomDetails.getStartDate());
        room.setEndDate(roomDetails.getEndDate());

        // Güncellenmiş odayı kaydet
        Room updatedRoom = roomService.saveRoom(room);
        return ResponseEntity.ok(updatedRoom);  // Güncellenmiş oda bilgisi ile 200 OK döndür
    }

    // Oda silmek için DELETE metodu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        Room room = roomService.findRoomById(id);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Oda bulunamadığında 404 döndür
        }
        roomService.deleteRoom(id);  // Odayı sil
        return ResponseEntity.noContent().build();  // 204 No Content döndür
    }

}



