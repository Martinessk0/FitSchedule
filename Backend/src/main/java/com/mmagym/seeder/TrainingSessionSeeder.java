package com.mmagym.seeder;


import com.mmagym.model.enums.SessionType;
import com.mmagym.room.Room;
import com.mmagym.room.repository.RoomRepository;
import com.mmagym.training_session.TrainingSession;
import com.mmagym.training_session.repository.TrainingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TrainingSessionSeeder implements Seeder {

    private final TrainingSessionRepository trainingSessionRepository;
    private final RoomRepository roomRepository;

    @Override
    public int order() {
        return 30;
    }

    @Override
    @Transactional
    public void seed() {
        if (trainingSessionRepository.count() > 0) return;

        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) throw new IllegalStateException("No rooms found. RoomSeeder must run first.");

        var sessions = new ArrayList<TrainingSession>();

        LocalDate startDay = LocalDate.now();
        int[] hours = {18, 19, 20};

        for (int d = 0; d < 14; d++) {
            for (int h : hours) {
                Room room = rooms.get(ThreadLocalRandom.current().nextInt(rooms.size()));
                SessionType type = SessionType.values()[ThreadLocalRandom.current().nextInt(SessionType.values().length)];

                LocalDateTime start = startDay.plusDays(d).atTime(h, 0);
                LocalDateTime end = start.plusMinutes(60);

                int capacity = Math.min(room.getCapacity(), 20);

                sessions.add(TrainingSession.builder()
                        .room(room)
                        .type(type)
                        .startTime(start)
                        .endTime(end)
                        .capacity(capacity)
                        .build());
            }
        }

        trainingSessionRepository.saveAll(sessions);
    }
}
