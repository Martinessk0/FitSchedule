package com.mmagym.membership.controller;

import com.mmagym.membership.dto.request.MembershipCreateRequest;
import com.mmagym.membership.dto.response.MembershipActiveResponse;
import com.mmagym.membership.dto.response.MembershipResponse;
import com.mmagym.membership.service.MembershipService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public ResponseEntity<MembershipResponse> purchase(
            @RequestBody(required = true)
            @Valid @org.springframework.web.bind.annotation.RequestBody MembershipCreateRequest request
    ) {
        MembershipResponse response = membershipService.purchase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public MembershipResponse getById(@PathVariable Long id) {
        return membershipService.getById(id);
    }

    @GetMapping("/active")
    public MembershipActiveResponse active(
            @RequestParam Long userId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        LocalDate effectiveDate = (date != null) ? date : LocalDate.now();
        boolean active = membershipService.hasActiveMembership(userId, effectiveDate);
        return MembershipActiveResponse.builder()
                .userId(userId)
                .date(effectiveDate)
                .active(active)
                .build();
    }
}
