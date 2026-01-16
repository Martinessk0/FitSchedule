package com.mmagym.training_session.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSessionCreateRequest {
    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private  LocalDateTime endTime;

    @NotNull
    private Integer capacity;

    @NotNull
    private Long roomId; //controlera ne vzima entity

    // validaciqta e tuk, ne v entitito

}
