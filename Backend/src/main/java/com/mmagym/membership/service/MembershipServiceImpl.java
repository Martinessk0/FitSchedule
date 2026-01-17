package com.mmagym.membership.service;

import com.mmagym.common.exception.BadRequestException;
import com.mmagym.common.exception.ConflictException;
import com.mmagym.common.exception.NotFoundException;
import com.mmagym.membership.Membership;
import com.mmagym.membership.dto.request.MembershipCreateRequest;
import com.mmagym.membership.dto.response.MembershipResponse;
import com.mmagym.membership.mapper.MembershipMapper;
import com.mmagym.membership.repository.MembershipRepository;
import com.mmagym.user.User;
import com.mmagym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public MembershipResponse purchase(MembershipCreateRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }

        if (request.getUserId() == null) {
            throw new BadRequestException("userId is required");
        }
        if (request.getType() == null) {
            throw new BadRequestException("type is required");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new BadRequestException("startDate and endDate are required");
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("endDate must be on/after startDate");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id=" + request.getUserId() + " not found"));

        boolean overlap = membershipRepository.existsOverlappingMembership(
                user.getId(),
                request.getStartDate(),
                request.getEndDate()
        );

        if (overlap) {
            throw new ConflictException("User already has a membership overlapping this period");
        }

        Membership membership = MembershipMapper.toEntity(request, user);
        Membership saved = membershipRepository.save(membership);
        return MembershipMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipResponse getById(Long id) {
        if (id == null) {
            throw new BadRequestException("id is required");
        }

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membership with id=" + id + " not found"));

        membership.getUser().getId(); // да дръпнем user-а преди mapper-а
        return MembershipMapper.toResponse(membership);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasActiveMembership(Long userId, LocalDate date) {
        if (userId == null) {
            throw new BadRequestException("userId is required");
        }
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        if (date == null) {
            date = LocalDate.now();
        }

        return membershipRepository.hasActiveMembership(userId, date);
    }
}
