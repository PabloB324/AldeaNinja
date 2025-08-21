package com.ninjaSystem.patterns.visitor;

import com.ninjaSystem.domain.Mission;
import com.ninjaSystem.domain.Ninja;
import com.ninjaSystem.domain.Jutsu;

public class TextExportVisitor implements ExportVisitor {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void visit(Ninja ninja) {
        sb.append("Ninja: ").append(ninja.getName())
                .append(" [").append(ninja.getRank()).append(" - ").append(ninja.getVillage()).append("] ")
                .append(" ATK=").append(ninja.getStats().getAttack())
                .append(" DEF=").append(ninja.getStats().getDefense())
                .append(" CHAKRA=").append(ninja.getStats().getChakra())
                .append("\n  Jutsus:\n");
        for (Jutsu j : ninja.getJutsus()) {
            sb.append("   - ").append(j.getName())
                    .append(" (coste ").append(j.getChakraCost()).append(")\n");
        }
    }

    @Override
    public void visit(Mission mission) {
        sb.append("Mission: ").append(mission.getName())
                .append(" [").append(mission.getRank()).append("] ")
                .append(" Reward=").append(mission.getReward())
                .append(" RequiredRank=").append(mission.getRequiredRank())
                .append("\n");
    }

    @Override
    public String getReport() { return sb.toString(); }
}
