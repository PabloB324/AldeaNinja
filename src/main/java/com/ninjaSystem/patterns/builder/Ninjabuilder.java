package com.ninjaSystem.patterns.builder;
import com.ninjaSystem.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Objects;

public class Ninjabuilder {
    private String name;
    private Rank rank =Rank.GENIN;
    private Village village = Village.KONOHA;
    private Stats stats = new Stats(5,5,10);
    private final List<Jutsu> jutsus = new ArrayList<>();
    public Ninjabuilder setName(String name){
        this.name = name;
        return this;
    }
    public Ninjabuilder setRank(Rank rank){
        this.rank = Objects.requireNonNull(rank);
        return this;
    }
    public Ninjabuilder setVillage(Village village){
        this.village = Objects.requireNonNull(village);
        return this;
    }
    public Ninjabuilder setStats(Stats stats){
        this.stats = Objects.requireNonNull(stats);
        return this;
    }
    public Ninjabuilder addJutsu(Jutsu jutsu){
        this.jutsus.add(Objects.requireNonNull(jutsu));
        return this;
    }
    public Ninjabuilder addJutsus(Collection<Jutsu> jutsus){
        if(jutsus!=null) for (Jutsu j : jutsus) addJutsu(j);
        return this;
    }
    public Ninjabuilder clearJutsus(){
        this.jutsus.clear();
        return this;
    }
    public Ninja build(){
        if(name == null || name.isBlank())
            throw new IllegalStateException("Es obligatorio asignra el nombre");
        return new Ninja(name, rank, village, stats, jutsus);
    }

}
