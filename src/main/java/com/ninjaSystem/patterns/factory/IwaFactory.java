package com.ninjaSystem.patterns.factory;

import com.ninjaSystem.domain.*;
import com.ninjaSystem.patterns.builder.Ninjabuilder;

public class IwaFactory implements AbstractNinjaFactory{
    @Override
    public Ninja createNinja(String name,Rank rank) {
        return new Ninjabuilder()
                .setName(name)
                .setRank(rank)
                .setVillage(Village.IWA)
                .setStats(new Stats(6,7,11))
                .addJutsu(new Jutsu("Doryuheki", 6))
                .addJutsu(new Jutsu("Kayugan no Jutsu",8))
                .build();
    }
}