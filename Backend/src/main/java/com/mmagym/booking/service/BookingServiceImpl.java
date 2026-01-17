package com.mmagym.booking.service;

import com.mmagym.booking.Booking;
import com.mmagym.booking.dto.request.BookingCreateRequest;
import com.mmagym.booking.dto.response.BookingResponse;
import com.mmagym.booking.mapper.BookingMapper;
import com.mmagym.booking.repository.BookingRepository;
import com.mmagym.common.enums.BookingStatus;
import com.mmagym.common.exception.BadRequestException;
import com.mmagym.common.exception.ConflictException;
import com.mmagym.common.exception.NotFoundException;
import com.mmagym.membership.repository.MembershipRepository;
import com.mmagym.training_session.TrainingSession;
import com.mmagym.training_session.repository.TrainingSessionRepository;
import com.mmagym.user.User;
import com.mmagym.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final MembershipRepository membershipRepository;

    @Override
    @Transactional
    public BookingResponse book (BookingCreateRequest request) {

        if (request == null) {
            throw new BadRequestException("Request body is required");
        }

        if (request.getUserId() == null) {
            throw new BadRequestException("userId is required");
        }

        if (request.getTrainingSessionId() == null) {
            throw new BadRequestException("trainingSessionId");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id=" + request.getUserId() + " not found"));

        TrainingSession trainingSession = trainingSessionRepository.findById(request.getTrainingSessionId())
                .orElseThrow(() -> new NotFoundException("Training session with id=" + request.getTrainingSessionId() + "not found"));


        boolean active = membershipRepository.hasActiveMembership(request.getUserId(), LocalDate.now());

        if (!active) throw new ConflictException("User has no active membership.");

        if(bookingRepository.existsByUserIdAndTrainingSessionId(user.getId(), trainingSession.getId()))
            throw new ConflictException("User has already booked this session");

        long bookedCount = bookingRepository.countByTrainingSessionIdAndStatus(trainingSession.getId(), BookingStatus.BOOKED);
        if (bookedCount >= trainingSession.getCapacity()) {
            throw new ConflictException("Session is full");
        }

        Booking booking = BookingMapper.toEntity(request,user, trainingSession);

        Booking saved = bookingRepository.save(booking);

        return BookingMapper.toResponse(saved);
    }

}
