package com.mmagym.room.service;

import com.mmagym.common.exception.BadRequestException;
import com.mmagym.common.exception.NotFoundException;
import com.mmagym.room.Room;
import com.mmagym.room.dto.request.RoomCreateRequest;
import com.mmagym.room.dto.response.RoomResponse;
import com.mmagym.room.mapper.RoomMapper;
import com.mmagym.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomResponse create(RoomCreateRequest request) {
        if (request == null) throw new BadRequestException("Request body is required");

        if (request.getName() == null) throw new BadRequestException("Room name  is required");

        if (request.getCapacity() < 0) throw new BadRequestException("Capacity should be more than 0");

        Room room = RoomMapper.toEntity(request);

        Room saved = roomRepository.save(room);

        return RoomMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long id) {
        if (id == null) throw new BadRequestException("id is required");

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room with id" + id + "not found"));

        return RoomMapper.toResponse(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(RoomMapper::toResponse)
                .toList();
    }
}
