package com.ninjaSystem.services;

import com.ninjaSystem.domain.Mission;
import com.ninjaSystem.domain.MissionRank;
import com.ninjaSystem.domain.Ninja;
import com.ninjaSystem.domain.Rank;

import com.ninjaSystem.patterns.factory.AbstractNinjaFactory;
import com.ninjaSystem.patterns.factory.IwaFactory;
import com.ninjaSystem.patterns.factory.KiriFactory;
import com.ninjaSystem.patterns.factory.KonohaFactory;
import com.ninjaSystem.patterns.factory.KumoFactory;
import com.ninjaSystem.patterns.factory.SunaFactory;

import com.ninjaSystem.patterns.visitor.ExportVisitor;
import com.ninjaSystem.patterns.visitor.JsonExportVisitor;
import com.ninjaSystem.patterns.visitor.TextExportVisitor;

public class Main {
    public static void main(String[] args) {
        // FACTORIES
        AbstractNinjaFactory konoha = new KonohaFactory();
        AbstractNinjaFactory suna   = new SunaFactory();
        AbstractNinjaFactory kiri   = new KiriFactory();
        AbstractNinjaFactory iwa    = new IwaFactory();
        AbstractNinjaFactory kumo   = new KumoFactory();

        // NINJAS
        Ninja naruto    = konoha.createNinja("Naruto",   Rank.GENIN);
        Ninja gaara     = suna.createNinja("Gaara",      Rank.CHUNIN);
        Ninja zabuza    = kiri.createNinja("Zabuza",     Rank.JONIN);
        Ninja deidara   = iwa.createNinja("Deidara",     Rank.JONIN);
        Ninja killerBee = kumo.createNinja("Killer Bee", Rank.JONIN);

        // ENTRENAMIENTO
        naruto.train();
        gaara.train();

        // COMBATE
        Ninja winner = CombatSimulator.fight(naruto, gaara);
        System.out.println("🏆 Ganador: " + winner.getName());

        // MISIÓN
        Mission mission = new Mission("Escoltar al maestro puente", MissionRank.C, 300, Rank.GENIN);

        // VISITOR TEXTO
        ExportVisitor textVisitor = new TextExportVisitor();
        naruto.accept(textVisitor);
        gaara.accept(textVisitor);
        mission.accept(textVisitor);
        System.out.println("\n== TEXT EXPORT ==\n" + textVisitor.getReport());

        // VISITOR JSON
        ExportVisitor jsonVisitor = new JsonExportVisitor();
        naruto.accept(jsonVisitor);
        gaara.accept(jsonVisitor);
        mission.accept(jsonVisitor);
        System.out.println("\n== JSON EXPORT ==\n" + jsonVisitor.getReport());
    }
}
