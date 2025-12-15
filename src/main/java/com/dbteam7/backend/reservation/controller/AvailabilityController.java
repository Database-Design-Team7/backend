package com.dbteam7.backend.reservation.controller;

import com.dbteam7.backend.reservation.dto.AvailabilityResponse;
import com.dbteam7.backend.reservation.service.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/facilities")
@Tag(name = "Reservation Availability", description = "예약 가능 시간 조회 API")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/{facilityId}/availability")
    @Operation(summary = "시설별 예약 가능 시간 조회", description = "특정 시설의 일자별 예약 가능/불가능 슬롯 현황을 조회합니다.")
    public ResponseEntity<?> getFacilityAvailability(
            @PathVariable String facilityId,
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        try {
            // 날짜가 제공되지 않으면 오늘 날짜 사용
            if (date == null) {
                date = LocalDate.now();
            }

            AvailabilityResponse response = availabilityService.getFacilityAvailability(facilityId, date);
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("예약 가능 시간 조회 중 오류가 발생했습니다."));
        }
    }

    // 에러 응답을 위한 record
    private record ErrorResponse(String message) {
    }
}

