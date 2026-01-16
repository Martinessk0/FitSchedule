package com.mmagym.seeder;

import com.mmagym.room.Room;
import com.mmagym.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomSeeder implements Seeder {

    private final RoomRepository roomRepository;

    @Override
    public int order() {
        return 10;
    }

    @Override
    @Transactional
    public void seed() {
        if (roomRepository.count() > 0) return;

        roomRepository.saveAll(List.of(
                Room.builder().name("Main Hall").capacity(30).build(),
                Room.builder().name("MMA Cage").capacity(20).build(),
                Room.builder().name("BJJ Mats").capacity(25).build()
        ));
    }
}
