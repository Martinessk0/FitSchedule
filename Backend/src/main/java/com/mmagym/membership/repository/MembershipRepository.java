package com.mmagym.membership.repository;

import com.mmagym.membership.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
