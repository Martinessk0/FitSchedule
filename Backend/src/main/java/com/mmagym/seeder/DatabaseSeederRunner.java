package com.mmagym.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Profile("seed")
@Component
@RequiredArgsConstructor
public class DatabaseSeederRunner implements CommandLineRunner {

    private final List<Seeder> seeders;

    @Override
    public void run(String... args) {
        seeders.stream()
                .sorted(Comparator.comparingInt(Seeder::order))
                .forEach(Seeder::seed);
    }
}