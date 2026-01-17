package com.mmagym.booking.controller;

import com.mmagym.booking.dto.request.BookingCreateRequest;
import com.mmagym.booking.dto.response.BookingResponse;
import com.mmagym.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> book (
            @RequestBody(required = true)
            @Valid BookingCreateRequest request
    ) {
        BookingResponse response = bookingService.book(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
