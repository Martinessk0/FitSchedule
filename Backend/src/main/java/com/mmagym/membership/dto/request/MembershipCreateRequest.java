package com.mmagym.membership.dto.request;

import com.mmagym.common.enums.MembershipType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipCreateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private MembershipType type;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
