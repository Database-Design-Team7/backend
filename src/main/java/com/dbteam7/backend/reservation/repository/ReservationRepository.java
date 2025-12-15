package com.dbteam7.backend.reservation.repository;

import com.dbteam7.backend.entity.Facility;
import com.dbteam7.backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    
    // 특정 시설의 특정 시간대 예약 조회
    List<Reservation> findByFacilityAndStartTimeLessThanAndEndTimeGreaterThan(
            Facility facility, 
            LocalDateTime endTime, 
            LocalDateTime startTime
    );
    
    // 특정 시설의 특정 날짜 예약 조회 (취소되지 않은 예약만)
    @Query("SELECT r FROM Reservation r WHERE r.facility = :facility " +
           "AND r.startTime >= :startDate AND r.startTime < :endDate " +
           "AND (r.status IS NULL OR r.status != 'CANCELLED')")
    List<Reservation> findActiveReservationsByFacilityAndDateRange(
            @Param("facility") Facility facility,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    // 특정 시설의 모든 활성 예약 조회
    List<Reservation> findByFacilityAndStatusNot(Facility facility, String status);
}

