package com.mmagym.seeder;

import com.mmagym.user.User;
import com.mmagym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSeeder implements Seeder {

    private final UserRepository userRepository;

    @Override
    public int order() {
        return 20;
    }

    @Override
    @Transactional
    public void seed() {
        if (userRepository.count() > 0) return;

        userRepository.saveAll(List.of(
                User.builder().email("ivan@mail.com").firstName("Ivan").lastName("Ivanov").build(),
                User.builder().email("petar@mail.com").firstName("Petar").lastName("Petrov").build(),
                User.builder().email("maria@mail.com").firstName("Maria").lastName("Georgieva").build(),
                User.builder().email("stefan@mail.com").firstName("Stefan").lastName("Dimitrov").build()
        ));
    }
}