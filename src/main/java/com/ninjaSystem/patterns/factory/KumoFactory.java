package com.ninjaSystem.patterns.factory;

import com.ninjaSystem.domain.*;
import com.ninjaSystem.patterns.builder.Ninjabuilder;

public class KumoFactory implements AbstractNinjaFactory{
    @Override
    public Ninja createNinja(String name,Rank rank) {
        return new Ninjabuilder()
                .setName(name)
                .setRank(rank)
                .setVillage(Village.KUMO)
                .setStats(new Stats(6,7,11))
                .addJutsu(new Jutsu("Rakiri", 10))
                .addJutsu(new Jutsu("Gian",7))
                .build();
    }
}