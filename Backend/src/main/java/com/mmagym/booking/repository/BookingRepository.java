package com.mmagym.booking.repository;

import com.mmagym.booking.Booking;
import com.mmagym.common.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUser_IdAndTrainingSession_Id(Long userId, Long trainingSessionId);
    long countByTrainingSession_IdAndStatus(Long trainingSessionId, BookingStatus status);
}
