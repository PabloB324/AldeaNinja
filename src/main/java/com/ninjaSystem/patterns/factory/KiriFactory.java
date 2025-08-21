package com.ninjaSystem.patterns.factory;

import com.ninjaSystem.domain.*;
import com.ninjaSystem.patterns.builder.Ninjabuilder;

public class KiriFactory implements AbstractNinjaFactory{
    @Override
    public Ninja createNinja(String name,Rank rank) {
        return new Ninjabuilder()
                .setName(name)
                .setRank(rank)
                .setVillage(Village.KIRI)
                .setStats(new Stats(6,7,11))
                .addJutsu(new Jutsu("Suiton", 9))
                .addJutsu(new Jutsu("Kirigakure no Jutsu",4))
                .build();
    }
}