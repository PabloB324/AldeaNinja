package com.ninjaSystem.patterns.visitor;

import com.ninjaSystem.domain.Mission;
import com.ninjaSystem.domain.Ninja;
import com.ninjaSystem.domain.Jutsu;

public class JsonExportVisitor implements ExportVisitor {
    private final StringBuilder sb = new StringBuilder();
    private boolean first = true;

    public JsonExportVisitor() {
        sb.append("{\"items\":[");
    }

    @Override
    public void visit(Ninja ninja) {
        if (!first) sb.append(",");
        sb.append("{\"type\":\"ninja\",")
                .append("\"name\":\"").append(ninja.getName()).append("\",")
                .append("\"rank\":\"").append(ninja.getRank()).append("\",")
                .append("\"village\":\"").append(ninja.getVillage()).append("\",")
                .append("\"stats\":{")
                .append("\"attack\":").append(ninja.getStats().getAttack()).append(",")
                .append("\"defense\":").append(ninja.getStats().getDefense()).append(",")
                .append("\"chakra\":").append(ninja.getStats().getChakra()).append("},")
                .append("\"jutsus\":[");
        for (int i = 0; i < ninja.getJutsus().size(); i++) {
            Jutsu j = ninja.getJutsus().get(i);
            sb.append("{\"name\":\"").append(j.getName()).append("\",")
                    .append("\"chakraCost\":").append(j.getChakraCost()).append("}");
            if (i < ninja.getJutsus().size() - 1) sb.append(",");
        }
        sb.append("]}");
        first = false;
    }

    @Override
    public void visit(Mission mission) {
        if (!first) sb.append(",");
        sb.append("{\"type\":\"mission\",")
                .append("\"name\":\"").append(mission.getName()).append("\",")
                .append("\"rank\":\"").append(mission.getRank()).append("\",")
                .append("\"reward\":").append(mission.getReward()).append(",")
                .append("\"requiredRank\":\"").append(mission.getRequiredRank()).append("\"")
                .append("}");
        first = false;
    }

    @Override
    public String getReport() {
        return sb.append("]}").toString();
    }
}
