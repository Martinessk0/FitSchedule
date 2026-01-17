package com.mmagym.room.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateRequest {
    @NotNull
    private String name;

    @NotNull
    private Integer capacity;
}
