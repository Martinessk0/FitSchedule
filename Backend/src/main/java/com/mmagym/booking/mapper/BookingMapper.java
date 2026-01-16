package com.mmagym.booking.mapper;

import com.mmagym.booking.Booking;
import com.mmagym.booking.dto.response.BookingResponse;

public final class BookingMapper {

    private BookingMapper() {}

    public static BookingResponse toResponse(Booking booking) {

        if (booking == null) return null;
        //TODO in Booking Service: da pravim userId i trainingSessionId v obekti
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                .trainingSessionId(booking.getTrainingSession() != null ? booking.getTrainingSession().getId() : null)
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
