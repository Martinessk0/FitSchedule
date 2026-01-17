package com.mmagym.training_session.controller;

import com.mmagym.common.enums.SessionType;
import com.mmagym.training_session.dto.request.TrainingSessionCreateRequest;
import com.mmagym.training_session.dto.response.TrainingSessionResponse;
import com.mmagym.training_session.service.TrainingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    public TrainingSessionController(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
    }

    @PostMapping
    public ResponseEntity<TrainingSessionResponse> create(
            @RequestBody(required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody TrainingSessionCreateRequest request
    ) {
        TrainingSessionResponse response = trainingSessionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<TrainingSessionResponse> list(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to,

            @RequestParam(required = false)
            SessionType type,

            @RequestParam(required = false)
            Long roomId
    ) {
        return trainingSessionService.list(from, to, type, roomId);
    }

    @GetMapping("/{id}")
    public TrainingSessionResponse getById(@PathVariable Long id) {
        return trainingSessionService.getById(id);
    }
}
