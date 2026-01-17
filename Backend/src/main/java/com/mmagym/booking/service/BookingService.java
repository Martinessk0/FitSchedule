package com.mmagym.booking.service;

import com.mmagym.booking.dto.request.BookingCreateRequest;
import com.mmagym.booking.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse book (BookingCreateRequest request);

}
