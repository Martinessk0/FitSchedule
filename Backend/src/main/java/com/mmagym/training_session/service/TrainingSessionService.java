package com.mmagym.training_session.service;

import com.mmagym.common.enums.SessionType;
import com.mmagym.training_session.dto.request.TrainingSessionCreateRequest;
import com.mmagym.training_session.dto.response.TrainingSessionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingSessionService {

    TrainingSessionResponse create(TrainingSessionCreateRequest request);

    TrainingSessionResponse getById(Long id);

    List<TrainingSessionResponse> list(LocalDateTime from, LocalDateTime to, SessionType type, Long roomId);
}