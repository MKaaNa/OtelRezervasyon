package com.MKaaN.OtelBackend.service.reservation;

import com.MKaaN.OtelBackend.dto.response.ReservationResponse;
import com.MKaaN.OtelBackend.dto.projection.ReservationSummary;
import com.MKaaN.OtelBackend.dto.request.PriceCalculationRequest;
import com.MKaaN.OtelBackend.dto.request.ReservationCreateRequest;
import com.MKaaN.OtelBackend.dto.request.ReservationUpdateRequest;
import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.mapper.ReservationMapper;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.service.spec.IReservationService;
import com.MKaaN.OtelBackend.util.DateUtils;
import com.MKaaN.OtelBackend.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponse createReservation(ReservationCreateRequest reservationRequest) {
        User user = userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + reservationRequest.getUserId()));
        Room room = roomRepository.findById(reservationRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found: " + reservationRequest.getRoomId()));

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .checkInTime(reservationRequest.getCheckInTime())
                .checkOutTime(reservationRequest.getCheckOutTime())
                .guestCount(reservationRequest.getGuestCount())
                .totalPrice(reservationRequest.getTotalPrice())
                .status(reservationRequest.getStatus())
                .specialRequests(reservationRequest.getSpecialRequests())
                .adminNote(reservationRequest.getAdminNote())
                .build();

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponse getReservationById(String id) {
        return reservationMapper.toDTO(reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + id)));
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getReservationsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return reservationRepository.findByUser(user).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getReservationsByRoomId(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));
        return reservationRepository.findByRoom(room).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponse updateReservation(String id, ReservationUpdateRequest updateRequest) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + id));

        if (updateRequest.getStartDate() != null) {
            reservation.setStartDate(updateRequest.getStartDate());
        }

        if (updateRequest.getEndDate() != null) {
            reservation.setEndDate(updateRequest.getEndDate());
        }

        if (updateRequest.getStatus() != null) {
            reservation.setStatus(updateRequest.getStatus());
        }

        if (updateRequest.getAdminNote() != null) {
            reservation.setAdminNote(updateRequest.getAdminNote());
        }

        if (updateRequest.getRoomId() != null) {
            Room room = roomRepository.findById(updateRequest.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found: " + updateRequest.getRoomId()));
            reservation.setRoom(room);
        }

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public void deleteReservation(String id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationResponse updateReservationStatus(String id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + id));
        reservation.setStatus(status);
        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public void cancelReservation(String id, String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + id));
        
        if (!isReservationOwner(id, email)) {
            throw new RuntimeException("You are not authorized to cancel this reservation");
        }
        
        reservation.cancel("Cancelled by user: " + email);
        reservationRepository.save(reservation);
    }

    @Override
    public boolean isReservationOwner(String reservationId, String email) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + reservationId));
        return reservation.getUser().getEmail().equals(email);
    }

    @Override
    public ReservationSummary getReservationSummaryById(String id) {
        // TODO: Implement reservation summary logic
        return null;
    }

    @Override
    public List<ReservationSummary> getReservationSummariesByUserId(String userId) {
        // TODO: Implement reservation summary logic
        return null;
    }

    @Override
    public List<ReservationSummary> getReservationSummariesByRoomId(String roomId) {
        // TODO: Implement reservation summary logic
        return null;
    }

    @Override
    public List<ReservationSummary> getReservationSummariesByStatus(ReservationStatus status) {
        // TODO: Implement reservation summary logic
        return null;
    }

    @Override
    public BigDecimal calculatePrice(PriceCalculationRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found: " + request.getRoomId()));

        if (!ValidationUtils.isValidReservationDateRange(request.getStartDate(), request.getEndDate())) {
            throw new IllegalArgumentException("Invalid date range");
        }

        return DateUtils.calculateTotalPrice(request.getStartDate(), request.getEndDate(), room.getPrice());
    }

    @Override
    public byte[] markReservationAsPaid(String id) {
        // TODO: Implement payment marking logic
        return null;
    }

    @Override
    public byte[] getInvoicePdf(String id) {
        // TODO: Implement invoice generation logic
        return null;
    }
}
