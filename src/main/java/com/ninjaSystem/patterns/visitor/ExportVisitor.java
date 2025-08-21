package com.ninjaSystem.patterns.visitor;

import com.ninjaSystem.domain.Mission;
import com.ninjaSystem.domain.Ninja;

public interface ExportVisitor {
    void visit(Ninja ninja);
    void visit(Mission mission);
    String getReport();
}
