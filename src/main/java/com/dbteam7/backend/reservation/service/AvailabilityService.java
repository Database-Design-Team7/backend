package com.dbteam7.backend.reservation.service;

import com.dbteam7.backend.entity.Facility;
import com.dbteam7.backend.entity.Reservation;
import com.dbteam7.backend.facility.repository.FacilityRepository;
import com.dbteam7.backend.reservation.dto.AvailabilityResponse;
import com.dbteam7.backend.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AvailabilityService {

    private final FacilityRepository facilityRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public AvailabilityService(FacilityRepository facilityRepository, 
                              ReservationRepository reservationRepository) {
        this.facilityRepository = facilityRepository;
        this.reservationRepository = reservationRepository;
    }

    public AvailabilityResponse getFacilityAvailability(String facilityId, LocalDate date) {
        // 시설 조회
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("시설을 찾을 수 없습니다: " + facilityId));

        // 해당 날짜의 시작과 끝 시간 설정 (하루 전체)
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // 해당 날짜의 활성 예약 조회
        List<Reservation> activeReservations = reservationRepository
                .findActiveReservationsByFacilityAndDateRange(facility, startOfDay, endOfDay);

        // 시간 슬롯 생성 (1시간 단위, 09:00 ~ 22:00)
        List<AvailabilityResponse.TimeSlot> timeSlots = generateTimeSlots(
                date, activeReservations
        );

        // 응답 생성
        AvailabilityResponse response = new AvailabilityResponse();
        response.setFacilityId(facility.getFacilityId());
        response.setFacilityName(facility.getFacilityName());
        response.setDate(date.toString());
        response.setTimeSlots(timeSlots);

        return response;
    }

    private List<AvailabilityResponse.TimeSlot> generateTimeSlots(
            LocalDate date, 
            List<Reservation> reservations
    ) {
        List<AvailabilityResponse.TimeSlot> slots = new ArrayList<>();
        
        // 운영시간: 09:00 ~ 22:00 (1시간 단위)
        LocalTime startHour = LocalTime.of(9, 0);
        LocalTime endHour = LocalTime.of(22, 0);

        for (int hour = startHour.getHour(); hour < endHour.getHour(); hour++) {
            LocalDateTime slotStart = date.atTime(hour, 0);
            LocalDateTime slotEnd = date.atTime(hour + 1, 0);

            AvailabilityResponse.TimeSlot slot = new AvailabilityResponse.TimeSlot();
            slot.setStartTime(slotStart);
            slot.setEndTime(slotEnd);

            // 해당 시간대에 예약이 있는지 확인
            boolean isReserved = reservations.stream().anyMatch(reservation ->
                    isTimeSlotOverlapping(reservation, slotStart, slotEnd)
            );

            slot.setAvailable(!isReserved);
            slot.setReason(isReserved ? "이미 예약됨" : null);

            slots.add(slot);
        }

        return slots;
    }

    private boolean isTimeSlotOverlapping(Reservation reservation, 
                                         LocalDateTime slotStart, 
                                         LocalDateTime slotEnd) {
        // 예약 시간과 슬롯 시간이 겹치는지 확인
        return !(reservation.getEndTime().isBefore(slotStart) || 
                 reservation.getStartTime().isAfter(slotEnd));
    }
}

