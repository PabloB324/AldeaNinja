package com.ninjaSystem.patterns.factory;

import com.ninjaSystem.domain.*;
import com.ninjaSystem.patterns.builder.Ninjabuilder;

public class KonohaFactory implements AbstractNinjaFactory{
    @Override
    public Ninja createNinja(String name,Rank rank) {
        return new Ninjabuilder()
                .setName(name)
                .setRank(rank)
                .setVillage(Village.KONOHA)
                .setStats(new Stats(7,7,12))
                .addJutsu(new Jutsu("Rasengan", 8))
                .addJutsu(new Jutsu("Kage Bushin",5))
                .build();
    }
}
