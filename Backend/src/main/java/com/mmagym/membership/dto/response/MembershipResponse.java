package com.mmagym.membership.dto.response;

import com.mmagym.model.enums.MembershipType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipResponse {
    private Long id;
    private Long userId;
    private MembershipType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

}
