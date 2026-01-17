package com.mmagym.training_session.service;

import com.mmagym.common.enums.SessionType;
import com.mmagym.common.exception.BadRequestException;
import com.mmagym.common.exception.NotFoundException;
import com.mmagym.room.Room;
import com.mmagym.room.repository.RoomRepository;
import com.mmagym.training_session.TrainingSession;
import com.mmagym.training_session.dto.request.TrainingSessionCreateRequest;
import com.mmagym.training_session.dto.response.TrainingSessionResponse;
import com.mmagym.training_session.mapper.TrainingSessionMapper;
import com.mmagym.training_session.repository.TrainingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public TrainingSessionResponse create(TrainingSessionCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }

        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new BadRequestException("startTime and endTime are required");
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new BadRequestException("endTime must be after startTime");
        }

        if (request.getCapacity() == null || request.getCapacity() <= 0) {
            throw new BadRequestException("capacity must be a positive number");
        }

        if (request.getRoomId() == null) {
            throw new BadRequestException("roomId is required");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room with id=" + request.getRoomId() + " not found"));

        if (room.getCapacity() != null && request.getCapacity() > room.getCapacity()) {
            throw new BadRequestException("session capacity cannot exceed room capacity (" + room.getCapacity() + ")");
        }

        TrainingSession session = TrainingSessionMapper.toEntity(request, room);

        TrainingSession saved = trainingSessionRepository.save(session);
        return TrainingSessionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingSessionResponse getById(Long id) {
        if (id == null) {
            throw new BadRequestException("id is required");
        }

        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TrainingSession with id=" + id + " not found"));

        // За да не гръмне lazy при mapper-а
        session.getRoom().getName();
        return TrainingSessionMapper.toResponse(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionResponse> list(LocalDateTime from, LocalDateTime to, SessionType type, Long roomId) {
        if (from != null && to != null && to.isBefore(from)) {
            throw new BadRequestException("'to' must be after 'from'");
        }

        if (roomId != null && !roomRepository.existsById(roomId)) {
            throw new NotFoundException("Room with id=" + roomId + " not found");
        }

        if (from == null && to != null) {
            from = to.minusDays(7);
        }
        if (to == null && from != null) {
            to = from.plusDays(7);
        }

        List<TrainingSession> sessions = trainingSessionRepository.findSchedule(from, to, type, roomId);

        return sessions.stream()
                .map(TrainingSessionMapper::toResponse)
                .toList();
    }
}
