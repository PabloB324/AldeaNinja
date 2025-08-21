package com.ninjaSystem.domain;

import com.ninjaSystem.patterns.visitor.ExportVisitor;

public class Mission {
    private final String name;
    private final MissionRank rank;
    private final int reward;
    private final Rank requiredRank;

    public Mission(String name, MissionRank rank, int reward, Rank requiredRank) {
        this.name = name;
        this.rank = rank;
        this.reward = reward;
        this.requiredRank = requiredRank;
    }

    public String getName() { return name; }
    public MissionRank getRank() { return rank; }
    public int getReward() { return reward; }
    public Rank getRequiredRank() { return requiredRank; }

    // 🔑 Método para Visitor
    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}
