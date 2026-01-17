package com.mmagym.room.service;

import com.mmagym.room.Room;
import com.mmagym.room.dto.request.RoomCreateRequest;
import com.mmagym.room.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse create (RoomCreateRequest request);

    RoomResponse getRoomById (Long id);

    List<RoomResponse> getAllRooms();
}
