package com.MKaaN.OtelBackend.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MKaaN.OtelBackend.dto.ApiResponse;
import com.MKaaN.OtelBackend.dto.RoomDTO;
import com.MKaaN.OtelBackend.service.room.IRoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;


    @GetMapping
    public ApiResponse<List<RoomDTO>> getAll() {
        return ApiResponse.success(roomService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RoomDTO> get(@PathVariable String id) {
        return ApiResponse.success(roomService.getById(id));
    }

    @PostMapping
    public ApiResponse<RoomDTO> add(@Valid @RequestBody RoomDTO roomDTO) {
        return ApiResponse.success(roomService.create(roomDTO));
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomDTO> edit(@PathVariable String id, @Valid @RequestBody RoomDTO roomDTO) {
        return ApiResponse.success(roomService.update(id, roomDTO));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        roomService.delete(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/available")
    public ApiResponse<List<RoomDTO>> getAvailable() {
        return ApiResponse.success(roomService.getAvailable());
    }
}
