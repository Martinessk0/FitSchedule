package com.mmagym.seeder;

import com.mmagym.booking.Booking;
import com.mmagym.booking.repository.BookingRepository;
import com.mmagym.common.enums.BookingStatus;
import com.mmagym.training_session.TrainingSession;
import com.mmagym.training_session.repository.TrainingSessionRepository;
import com.mmagym.user.User;
import com.mmagym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class BookingSeeder implements Seeder {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TrainingSessionRepository trainingSessionRepository;

    @Override
    public int order() {
        return 50;
    }

    @Override
    @Transactional
    public void seed() {
        if (bookingRepository.count() > 0) return;

        List<User> users = userRepository.findAll();
        List<TrainingSession> sessions = trainingSessionRepository.findAll();
        if (users.isEmpty() || sessions.isEmpty()) return;

        Map<Long, Integer> used = new HashMap<>();
        var bookings = new ArrayList<Booking>();

        for (User u : users) {
            int wanted = ThreadLocalRandom.current().nextInt(2, 6);

            int attempts = 0;
            while (wanted > 0 && attempts < 50) {
                attempts++;

                TrainingSession s = sessions.get(ThreadLocalRandom.current().nextInt(sessions.size()));
                int current = used.getOrDefault(s.getId(), 0);

                if (current >= s.getCapacity()) continue;
                if (bookingRepository.existsByUser_IdAndTrainingSession_Id(u.getId(), s.getId())) continue;

                bookings.add(Booking.builder()
                        .user(u)
                        .trainingSession(s)
                        .status(BookingStatus.BOOKED)
                        .build());

                used.put(s.getId(), current + 1);
                wanted--;
            }
        }

        bookingRepository.saveAll(bookings);
    }
}