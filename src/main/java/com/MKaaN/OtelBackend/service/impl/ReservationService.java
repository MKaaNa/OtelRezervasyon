package com.MKaaN.OtelBackend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MKaaN.OtelBackend.dto.PriceCalculationRequest;
import com.MKaaN.OtelBackend.dto.ReservationDTO;
import com.MKaaN.OtelBackend.dto.projection.ReservationSummary;
import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.exception.BadRequestException;
import com.MKaaN.OtelBackend.exception.InvoiceGenerationException;
import com.MKaaN.OtelBackend.exception.NotFoundException;
import com.MKaaN.OtelBackend.mapper.ReservationMapper;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.service.IReservationService;
import com.MKaaN.OtelBackend.service.invoice.InvoiceService;
import com.MKaaN.OtelBackend.util.DateUtils;

@Service
@Transactional
public class ReservationService implements IReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final InvoiceService invoiceService;

    // Explicity constructor instead of Lombok's @RequiredArgsConstructor
    public ReservationService(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            RoomRepository roomRepository,
            InvoiceService invoiceService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.invoiceService = invoiceService;
    }

    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        logger.info("Rezervasyon oluşturuluyor: oda={}, kullanıcı={}",
            reservationDTO.getRoomId(), reservationDTO.getUserId());
        
        Room room = findRoomById(reservationDTO.getRoomId());
        User user = findUserById(reservationDTO.getUserId());
        validateRoomAvailability(room.getId(), reservationDTO.getStartDate(), reservationDTO.getEndDate());
        
        Reservation reservation = createReservationEntity(reservationDTO, room, user);
        Reservation savedReservation = reservationRepository.save(reservation);
        
        logger.info("Rezervasyon başarıyla oluşturuldu: id={}", savedReservation.getId());
        return ReservationMapper.toDTO(savedReservation);
    }

    private Room findRoomById(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Oda bulunamadı"));
    }

    private User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı"));
    }

    private void validateRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        if (!DateUtils.isDateRangeValid(startDate, endDate)) {
            throw new BadRequestException("Geçersiz tarih aralığı. Başlangıç tarihi bitiş tarihinden önce olmalıdır.");
        }

        if (!DateUtils.isDateInFuture(startDate)) {
            throw new BadRequestException("Başlangıç tarihi gelecekte olmalıdır.");
        }

        if (!isRoomAvailable(roomId, startDate, endDate)) {
            throw new BadRequestException("Oda seçilen tarihler için uygun değil");
        }
    }

    private boolean isRoomAvailable(String roomId, LocalDate startDate, LocalDate endDate) {
        // Odanın belirtilen tarih aralığında müsait olup olmadığını kontrol et
        return reservationRepository.findOverlappingReservations(
                roomId, startDate, endDate).isEmpty();
    }

    private Reservation createReservationEntity(ReservationDTO dto, Room room, User user) {
        Reservation reservation = ReservationMapper.toEntity(dto);
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.PENDING);

        // Toplam fiyatı hesapla
        PriceCalculationRequest priceRequest = new PriceCalculationRequest(
                room.getId(), dto.getStartDate(), dto.getEndDate());
        reservation.setTotalPrice(calculatePrice(priceRequest));

        return reservation;
    }

    @Override
    @Transactional
    public ReservationDTO updateReservation(String id, ReservationDTO reservationDTO) {
        Reservation existingReservation = findReservationById(id);
        validateRoomAvailability(existingReservation.getRoom().getId(),
                reservationDTO.getStartDate(), reservationDTO.getEndDate());

        updateReservationEntity(existingReservation, reservationDTO);
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        return ReservationMapper.toDTO(updatedReservation);
    }

    private Reservation findReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rezervasyon bulunamadı"));
    }

    private void updateReservationEntity(Reservation reservation, ReservationDTO dto) {
        ReservationMapper.updateEntityFromDTO(reservation, dto);

        // Toplam fiyatı güncelle
        PriceCalculationRequest priceRequest = new PriceCalculationRequest(
                reservation.getRoom().getId(), dto.getStartDate(), dto.getEndDate());
        reservation.setTotalPrice(calculatePrice(priceRequest));
    }

    @Override
    @Transactional
    public void deleteReservation(String id) {
        logger.info("Rezervasyon siliniyor: id={}", id);

        if (!reservationRepository.existsById(id)) {
            logger.warn("Var olmayan rezervasyon silinmeye çalışıldı: id={}", id);
            throw new NotFoundException("Rezervasyon bulunamadı");
        }
        
        reservationRepository.deleteById(id);
        logger.info("Rezervasyon başarıyla silindi: id={}", id);
    }

    @Override
    public ReservationDTO getReservationById(String id) {
        Reservation reservation = findReservationById(id);
        return ReservationMapper.toDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    } //Projection'a çevir

    @Override
    public List<ReservationDTO> getReservationsByUserId(String userId) {
        // Kullanıcının varlığını kontrol et
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }
        //UserValidator hazırla bunları oraya koy

        return mapReservationsToDTO(reservationRepository.findByUserId(userId));
    }

    private List<ReservationDTO> mapReservationsToDTO(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByRoomId(String roomId) {
        // Odanın varlığını kontrol et
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Oda bulunamadı");
        }

        return mapReservationsToDTO(reservationRepository.findByRoomId(roomId));
    }

    @Override
    public BigDecimal calculatePrice(PriceCalculationRequest request) {
        Room room = findRoomById(request.getRoomId());
        return DateUtils.calculateTotalPrice(request.getStartDate(), request.getEndDate(), room.getPrice());
    }

    @Override
    @Transactional
    public ReservationDTO updateReservationStatus(String id, ReservationStatus status) {
        Reservation reservation = findReservationById(id);
        reservation.setStatus(status);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(updatedReservation);
    }

    @Override
    public List<ReservationDTO> getReservationsByStatus(ReservationStatus status) {
        return mapReservationsToDTO(reservationRepository.findByStatus(status));
    }

    @Override
    public byte[] markReservationAsPaid(String id) {
        Reservation reservation = findReservationById(id);

        // Durumu ödendi olarak güncelle
        reservation.setStatus(ReservationStatus.PAID);
        reservationRepository.save(reservation);

        // Fatura oluştur
        try {
            return invoiceService.generateInvoice(id);
        } catch (Exception e) {
            logger.error("Fatura oluşturma hatası: {}", e.getMessage(), e);
            throw new InvoiceGenerationException("Fatura oluşturulamadı: " + e.getMessage());
        }
    }

    @Override
    public byte[] getInvoicePdf(String id) {
        // Rezervasyonun varlığını kontrol et
        findReservationById(id);

        // Fatura oluştur
        try {
            return invoiceService.generateInvoicePdf(id);
        } catch (Exception e) {
            logger.error("Fatura oluşturma hatası: {}", e.getMessage(), e);
            throw new InvoiceGenerationException("Fatura oluşturulamadı: " + e.getMessage());
        }
    }

    @Override
    public ReservationSummary getReservationSummaryById(String id) {
        return reservationRepository.findSummaryById(id)
                .orElseThrow(() -> new NotFoundException("Rezervasyon bulunamadı"));
    }

    @Override
    public List<ReservationSummary> getReservationSummariesByUserId(String userId) {
        // Kullanıcının varlığını kontrol et
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Kullanıcı bulunamadı");
        }

        return reservationRepository.findSummariesByUserId(userId);
    }

    @Override
    public List<ReservationSummary> getReservationSummariesByRoomId(String roomId) {
        // Odanın varlığını kontrol et
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Oda bulunamadı");
        }

        return reservationRepository.findSummariesByRoomId(roomId);
    }

    @Override
    public List<ReservationSummary> getReservationSummariesByStatus(ReservationStatus status) {
        return reservationRepository.findSummariesByStatus(status);
    }
}
