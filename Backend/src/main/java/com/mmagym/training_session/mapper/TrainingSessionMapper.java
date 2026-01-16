package com.mmagym.training_session.mapper;

import com.mmagym.training_session.TrainingSession;
import com.mmagym.training_session.dto.response.TrainingSessionResponse;

public final class TrainingSessionMapper {
    private TrainingSessionMapper() {}

    public static TrainingSessionResponse toResponse(TrainingSession session) {
        if (session == null) return null;

        return TrainingSessionResponse.builder()
                .id(session.getId())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .capacity(session.getCapacity())
                .type(session.getType())
                .roomId(session.getRoom() != null ? session.getRoom().getId() : null)
                .roomName(session.getRoom() != null ? session.getRoom().getName() : null)
                .build();
    }
}
