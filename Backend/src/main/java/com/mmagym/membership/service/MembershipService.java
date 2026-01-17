package com.mmagym.membership.service;

import com.mmagym.membership.dto.request.MembershipCreateRequest;
import com.mmagym.membership.dto.response.MembershipResponse;

import java.time.LocalDate;

public interface MembershipService {

    MembershipResponse purchase(MembershipCreateRequest request);

    MembershipResponse getById(Long id);

    boolean hasActiveMembership(Long userId, LocalDate date);
}
