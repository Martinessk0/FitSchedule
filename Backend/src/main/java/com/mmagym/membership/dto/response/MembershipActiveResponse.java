package com.mmagym.membership.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipActiveResponse {
    private Long userId;
    private LocalDate date;
    private boolean active;
}
