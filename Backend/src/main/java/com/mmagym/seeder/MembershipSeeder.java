package com.mmagym.seeder;


import com.mmagym.membership.Membership;
import com.mmagym.membership.repository.MembershipRepository;
import com.mmagym.model.enums.MembershipType;
import com.mmagym.user.User;
import com.mmagym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MembershipSeeder implements Seeder {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    @Override
    public int order() {
        return 40;
    }

    @Override
    @Transactional
    public void seed() {
        if (membershipRepository.count() > 0) return;

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new IllegalStateException("No users found. UserSeeder must run first.");

        LocalDate today = LocalDate.now();
        var memberships = new ArrayList<Membership>();

        for (User u : users) {
            memberships.add(Membership.builder()
                    .user(u)
                    .startDate(today.minusDays(5))
                    .endDate(today.plusDays(25))
                    .type(MembershipType.MONTHLY)
                    .build());
        }

        membershipRepository.saveAll(memberships);
    }
}
