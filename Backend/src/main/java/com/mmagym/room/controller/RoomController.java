package com.mmagym.room.controller;

import com.mmagym.room.dto.request.RoomCreateRequest;
import com.mmagym.room.dto.response.RoomResponse;
import com.mmagym.room.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create (
            @RequestBody(required = true)
            @Valid RoomCreateRequest request
    ) {
        RoomResponse response = roomService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById (@PathVariable Long id) {return roomService.getRoomById(id);}

    @GetMapping
    public List<RoomResponse> getAllUsers () {return roomService.getAllRooms();}
}
