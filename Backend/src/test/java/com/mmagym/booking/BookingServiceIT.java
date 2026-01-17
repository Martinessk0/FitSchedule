package com.mmagym.booking;

import com.mmagym.booking.dto.request.BookingCreateRequest;
import com.mmagym.booking.repository.BookingRepository;
import com.mmagym.booking.service.BookingService;
import com.mmagym.common.enums.BookingStatus;
import com.mmagym.common.enums.SessionType;
import com.mmagym.common.exception.ConflictException;
import com.mmagym.membership.Membership;
import com.mmagym.membership.repository.MembershipRepository;
import com.mmagym.room.Room;
import com.mmagym.room.repository.RoomRepository;
import com.mmagym.training_session.TrainingSession;
import com.mmagym.training_session.repository.TrainingSessionRepository;
import com.mmagym.user.User;
import com.mmagym.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class BookingServiceIT {
    @Autowired private BookingService bookingService;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private TrainingSessionRepository trainingSessionRepository;
    @Autowired private MembershipRepository membershipRepository;

    @BeforeEach
    void cleanDb() {
        bookingRepository.deleteAll();
        membershipRepository.deleteAll();
        trainingSessionRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void bookingSucceeds_whenUserHasActiveMembership_andSessionHasFreeSpots() {
        User user = userRepository.save(User.builder()
                .email("test1@mail.com")
                .firstName("Test")
                .lastName("User")
                .build());

        Room room = roomRepository.save(Room.builder()
                .name("Room A")
                .capacity(20)
                .build());

        TrainingSession session = trainingSessionRepository.save(TrainingSession.builder()
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .capacity(10)
                .type(SessionType.MMA)
                .room(room)
                .build());

        membershipRepository.save(Membership.builder()
                .user(user)
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(30))
                .type(com.mmagym.common.enums.MembershipType.MONTHLY)
                .build());

        BookingCreateRequest req = BookingCreateRequest.builder()
                .userId(user.getId())
                .trainingSessionId(session.getId())
                .build();

        var resp = bookingService.book(req);

        assertNotNull(resp.getBookingId());
        assertEquals(user.getId(), resp.getUserId());
        assertEquals(session.getId(), resp.getTrainingSessionId());
        assertEquals(BookingStatus.BOOKED, resp.getStatus());
        assertNotNull(resp.getCreatedAt());
    }

    @Test
    void bookingFails_whenUserHasNoActiveMembership() {
        User user = userRepository.save(User.builder()
                .email("test2@mail.com")
                .firstName("No")
                .lastName("Membership")
                .build());

        Room room = roomRepository.save(Room.builder()
                .name("Room B")
                .capacity(20)
                .build());

        TrainingSession session = trainingSessionRepository.save(TrainingSession.builder()
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .capacity(10)
                .type(SessionType.BJJ)
                .room(room)
                .build());

        BookingCreateRequest req = BookingCreateRequest.builder()
                .userId(user.getId())
                .trainingSessionId(session.getId())
                .build();

        assertThrows(ConflictException.class, () -> bookingService.book(req));
    }

    @Test
    void bookingFails_whenSessionIsFull() {
        User user1 = userRepository.save(User.builder().email("a@mail.com").firstName("A").lastName("A").build());
        User user2 = userRepository.save(User.builder().email("b@mail.com").firstName("B").lastName("B").build());

        Room room = roomRepository.save(Room.builder().name("Room C").capacity(10).build());

        TrainingSession session = trainingSessionRepository.save(TrainingSession.builder()
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .capacity(1)
                .type(SessionType.BOXING)
                .room(room)
                .build());

        membershipRepository.save(Membership.builder()
                .user(user1).type(com.mmagym.common.enums.MembershipType.MONTHLY)
                .startDate(LocalDate.now().minusDays(1)).endDate(LocalDate.now().plusDays(30)).build());

        membershipRepository.save(Membership.builder()
                .user(user2).type(com.mmagym.common.enums.MembershipType.MONTHLY)
                .startDate(LocalDate.now().minusDays(1)).endDate(LocalDate.now().plusDays(30)).build());

        bookingService.book(BookingCreateRequest.builder()
                .userId(user1.getId()).trainingSessionId(session.getId()).build());

        assertThrows(ConflictException.class, () -> bookingService.book(
                BookingCreateRequest.builder()
                        .userId(user2.getId())
                        .trainingSessionId(session.getId())
                        .build()
        ));
    }

    @Test
    void bookingFails_whenDuplicateBooking() {
        User user = userRepository.save(User.builder()
                .email("dup@mail.com").firstName("Dup").lastName("User").build());

        Room room = roomRepository.save(Room.builder().name("Room D").capacity(20).build());

        TrainingSession session = trainingSessionRepository.save(TrainingSession.builder()
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .capacity(10)
                .type(SessionType.KICKBOXING)
                .room(room)
                .build());

        membershipRepository.save(Membership.builder()
                .user(user).type(com.mmagym.common.enums.MembershipType.MONTHLY)
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(30))
                .build());

        BookingCreateRequest req = BookingCreateRequest.builder()
                .userId(user.getId())
                .trainingSessionId(session.getId())
                .build();

        bookingService.book(req);
        assertThrows(ConflictException.class, () -> bookingService.book(req));
    }
}
