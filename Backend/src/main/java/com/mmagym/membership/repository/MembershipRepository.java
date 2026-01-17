package com.mmagym.membership.repository;

import com.mmagym.membership.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("""
            select (count(m) > 0) from Membership m
            where m.user.id = :userId
              and :date between m.startDate and m.endDate
            """)
    boolean hasActiveMembership(Long userId, LocalDate date);

    @Query("""
            select (count(m) > 0) from Membership m
            where m.user.id = :userId
              and m.startDate <= :endDate
              and m.endDate >= :startDate
            """)
    boolean existsOverlappingMembership(Long userId, LocalDate startDate, LocalDate endDate);
}
