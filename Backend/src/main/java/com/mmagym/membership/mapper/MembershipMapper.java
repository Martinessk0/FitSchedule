package com.mmagym.membership.mapper;

import com.mmagym.membership.Membership;
import com.mmagym.membership.dto.response.MembershipResponse;

import java.time.LocalDate;

public final class MembershipMapper {
    private MembershipMapper() {}

    public static MembershipResponse toResponse(Membership membership) {
        if(membership == null) return null;

        LocalDate today = LocalDate.now();
        boolean active = membership.getStartDate() != null
                && membership.getEndDate() != null
                && !today.isBefore(membership.getStartDate())
                && !today.isAfter(membership.getEndDate());

        return MembershipResponse.builder()
                .id(membership.getId())
                .userId(membership.getUser() != null ? membership.getUser().getId() : null)
                .type(membership.getType())
                .startDate(membership.getStartDate())
                .endDate(membership.getEndDate())
                .active(active)
                .build();
    }
}
