package com.mmagym.booking.dto.response;

import com.mmagym.model.enums.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long trainingSessionId;
    private BookingStatus status;
    private LocalDateTime createdAt;

}
