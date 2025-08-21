package com.ninjaSystem.domain;

import com.ninjaSystem.patterns.visitor.ExportVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ninja {
    private final String name;
    private final Rank rank;
    private final Village village;
    private final Stats stats;
    private final List<Jutsu> jutsus;

    public Ninja(String name, Rank rank, Village village, Stats stats, List<Jutsu> jutsus) {
        this.name = name;
        this.rank = rank;
        this.village = village;
        this.stats = stats;
        this.jutsus = new ArrayList<>(jutsus);
    }

    public String getName() { return name; }
    public Rank getRank() { return rank; }
    public Village getVillage() { return village; }
    public Stats getStats() { return stats; }
    public List<Jutsu> getJutsus() { return Collections.unmodifiableList(jutsus); }

    // Entrenamiento
    public void train() {
        stats.train(1, 1, 2);
    }

    // Combate simple
    public Ninja simulateCombat(Ninja opponent) {
        int myPower = stats.getAttack() + stats.getDefense() + stats.getChakra() / 2;
        int opPower = opponent.stats.getAttack() + opponent.stats.getDefense() + opponent.stats.getChakra() / 2;
        return (myPower >= opPower) ? this : opponent;
    }

    // 🔑 Método para Visitor
    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}
