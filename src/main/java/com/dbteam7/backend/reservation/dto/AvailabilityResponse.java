package com.dbteam7.backend.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {
    private String facilityId;
    private String facilityName;
    private String date;
    private List<TimeSlot> timeSlots;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeSlot {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private boolean available;
        private String reason; // available이 false일 때 이유 (예: "이미 예약됨", "운영시간 외")
    }
}

