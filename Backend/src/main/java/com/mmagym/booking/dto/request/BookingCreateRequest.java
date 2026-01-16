package com.mmagym.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long trainingSessionId;

    //posle biznes logica v servica
}
