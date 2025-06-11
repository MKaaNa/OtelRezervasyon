package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.dto.request.ReservationCreateRequest;
import com.MKaaN.OtelBackend.dto.request.ReservationUpdateRequest;
import com.MKaaN.OtelBackend.dto.response.ReservationResponse;
import com.MKaaN.OtelBackend.service.spec.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationService.isReservationOwner(#id, authentication.principal.email)")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable String id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.email")
    public ResponseEntity<List<ReservationResponse>> getReservationsByUser(@PathVariable String email) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(email));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.createReservation(reservationRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationService.isReservationOwner(#id, authentication.principal.email)")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable String id, @RequestBody ReservationUpdateRequest updateRequest) {
        return ResponseEntity.ok(reservationService.updateReservation(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationService.isReservationOwner(#id, authentication.principal.email)")
    public ResponseEntity<Void> cancelReservation(@PathVariable String id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        reservationService.cancelReservation(id, email);
        return ResponseEntity.ok().build();
    }
}
