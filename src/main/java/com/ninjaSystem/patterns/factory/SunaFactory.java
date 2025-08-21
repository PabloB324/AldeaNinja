package com.ninjaSystem.patterns.factory;

import com.ninjaSystem.domain.*;
import com.ninjaSystem.patterns.builder.Ninjabuilder;

public class SunaFactory implements AbstractNinjaFactory{
    @Override
    public Ninja createNinja(String name,Rank rank) {
        return new Ninjabuilder()
                .setName(name)
                .setRank(rank)
                .setVillage(Village.SUNA)
                .setStats(new Stats(6,7,11))
                .addJutsu(new Jutsu("Sabaky Kyuu", 7))
                .addJutsu(new Jutsu("Futoon Sabaku Kyuu",6))
                .build();
    }
}