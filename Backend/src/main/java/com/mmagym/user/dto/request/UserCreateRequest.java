package com.mmagym.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
