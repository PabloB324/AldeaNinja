package com.ninjaSystem.services;

import com.ninjaSystem.domain.Ninja;

public class CombatSimulator {
    public static Ninja fight(Ninja n1, Ninja n2) {
        int power1 = n1.getStats().getAttack() + n1.getStats().getDefense() + n1.getStats().getChakra() / 2;
        int power2 = n2.getStats().getAttack() + n2.getStats().getDefense() + n2.getStats().getChakra() / 2;

        System.out.println("Combate entre " + n1.getName() + " y " + n2.getName());
        System.out.println(" - Poder de " + n1.getName() + ": " + power1);
        System.out.println(" - Poder de " + n2.getName() + ": " + power2);

        return (power1 >= power2) ? n1 : n2;
    }
}
