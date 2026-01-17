package com.mmagym.booking.mapper;

import com.mmagym.booking.Booking;
import com.mmagym.booking.dto.request.BookingCreateRequest;
import com.mmagym.booking.dto.response.BookingResponse;
import com.mmagym.common.enums.BookingStatus;
import com.mmagym.training_session.TrainingSession;
import com.mmagym.user.User;

public final class BookingMapper {

    private BookingMapper() {}

    public static Booking toEntity (BookingCreateRequest request, User user, TrainingSession trainingSession) {

        if (request == null) return null;

        return Booking.builder()
                .user(user)
                .trainingSession(trainingSession)
                .status(BookingStatus.BOOKED)
                .build();
    }

    public static BookingResponse toResponse(Booking booking) {

        if (booking == null) return null;

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                .trainingSessionId(booking.getTrainingSession() != null ? booking.getTrainingSession().getId() : null)
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
