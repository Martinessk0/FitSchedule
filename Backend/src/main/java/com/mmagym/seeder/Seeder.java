package com.mmagym.seeder;

public interface Seeder {
    int order();     // по-малко число = по-рано
    void seed();
}
