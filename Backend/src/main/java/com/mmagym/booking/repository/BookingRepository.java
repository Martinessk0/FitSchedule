package com.mmagym.booking.repository;

import com.mmagym.booking.Booking;
import com.mmagym.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUserIdAndTrainingSessionId(Long userId, Long trainingSessionId);
    long countByTrainingSessionIdAndStatus(Long trainingSessionId, BookingStatus status);
}
