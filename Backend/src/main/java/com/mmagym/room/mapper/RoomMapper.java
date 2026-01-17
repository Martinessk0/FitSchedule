package com.mmagym.room.mapper;

import com.mmagym.room.Room;
import com.mmagym.room.dto.request.RoomCreateRequest;
import com.mmagym.room.dto.response.RoomResponse;

public final class RoomMapper {
    private RoomMapper () {}

    public static Room toEntity (RoomCreateRequest request) {
        if (request == null) return null;

        return Room.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .build();
    }

    public static RoomResponse toResponse (Room room) {
        if (room == null) return null;

        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .build();
    }
}
