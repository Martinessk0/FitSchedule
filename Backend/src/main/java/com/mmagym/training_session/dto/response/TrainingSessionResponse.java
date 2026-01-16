package com.mmagym.training_session.dto.response;

import com.mmagym.common.enums.SessionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSessionResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer capacity;
    private SessionType type;

    private Long roomId;
    private String roomName;

}
