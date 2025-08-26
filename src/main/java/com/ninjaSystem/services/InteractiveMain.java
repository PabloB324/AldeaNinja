package com.ninjaSystem.services;

import com.ninjaSystem.domain.Mission;
import com.ninjaSystem.domain.MissionRank;
import com.ninjaSystem.domain.Ninja;
import com.ninjaSystem.domain.Rank;
import com.ninjaSystem.patterns.factory.*;

import java.util.*;

public class InteractiveMain {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Ninja> availableNinjas = new ArrayList<>();
    private static TrainingService trainingService = new TrainingService();
    private static List<Mission> availableMissions = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("--- ¡Bienvenido al Sistema de Entrenamiento y Combate Ninja! ---");
        System.out.println("═══════════════════════════════════════════════════════════════");
        
        initializeNinjas();
        initializeMissions();
        
        boolean continueGame = true;
        while (continueGame) {
            showMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    startTrainingAndBattle();
                    break;
                case 2:
                    showAllNinjas();
                    break;
                case 3:
                    selectMissionForNinja();
                    break;
                case 4:
                    continueGame = false;
                    System.out.println("--- ¡Gracias por usar el Sistema Ninja! ¡Hasta la próxima! ---");
                    break;
                default:
                    System.out.println("--- Opción inválida. Por favor, elige una opción válida. ---");
            }
        }
        
        scanner.close();
    }
    
    private static void initializeNinjas() {
        System.out.println("Inicializando ninjas de las cinco aldeas...\n");
        
        // Crear factories
        AbstractNinjaFactory konoha = new KonohaFactory();
        AbstractNinjaFactory suna = new SunaFactory();
        AbstractNinjaFactory kiri = new KiriFactory();
        AbstractNinjaFactory iwa = new IwaFactory();
        AbstractNinjaFactory kumo = new KumoFactory();
        
        // Crear ninjas con diferentes rangos
        availableNinjas.add(konoha.createNinja("Naruto", Rank.GENIN));
        availableNinjas.add(konoha.createNinja("Sasuke", Rank.GENIN));
        availableNinjas.add(konoha.createNinja("Kakashi", Rank.JONIN));
        
        availableNinjas.add(suna.createNinja("Gaara", Rank.CHUNIN));
        availableNinjas.add(suna.createNinja("Temari", Rank.CHUNIN));
        
        availableNinjas.add(kiri.createNinja("Zabuza", Rank.JONIN));
        availableNinjas.add(kiri.createNinja("Haku", Rank.GENIN));
        
        availableNinjas.add(iwa.createNinja("Deidara", Rank.JONIN));
        availableNinjas.add(iwa.createNinja("Onoki", Rank.JONIN));
        
        availableNinjas.add(kumo.createNinja("Killer Bee", Rank.JONIN));
        availableNinjas.add(kumo.createNinja("Ay", Rank.JONIN));
    }

    private static void initializeMissions() {
        System.out.println("Inicializando misiones disponibles...\n");
        
        // Crear misiones de ejemplo
        availableMissions.add(new Mission("Rescate del Gato Perdido", MissionRank.D, 100, Rank.GENIN));
        availableMissions.add(new Mission("Escolta de Mercaderes", MissionRank.C, 300, Rank.CHUNIN));
        availableMissions.add(new Mission("Infiltración Enemiga", MissionRank.A, 800, Rank.JONIN));
        availableMissions.add(new Mission("Caza de Bandidos", MissionRank.B, 500, Rank.CHUNIN));
        availableMissions.add(new Mission("Protección del Hokage", MissionRank.S, 1000, Rank.JONIN));
        availableMissions.add(new Mission("Entrega de Mensaje", MissionRank.D, 150, Rank.GENIN));
    }

    private static void showMenu() {
        System.out.println("\n═══ MENÚ PRINCIPAL ═══\n1. Entrenar ninja y combatir\n2. Ver todos los ninjas disponibles\n" + 
            "3. Enviar ninja a mision\n4. Salir\nSelecciona una opción: ");
    }
    
    private static void selectMissionForNinja() {
        System.out.println("\n¡Es hora de enviar un ninja a una misión!");
        System.out.println("═══════════════════════════════════════");
        
        // Seleccionar ninja
        Ninja selectedNinja = selectNinja();
        if (selectedNinja == null) return;
        
        System.out.println("\nNinja seleccionado: " + selectedNinja.getName() + 
                          " (Rango: " + selectedNinja.getRank() + ")");
        
        // Seleccionar misión
        Mission selectedMission = selectMission();
        if (selectedMission == null) return;
        
        // Verificar si el ninja puede realizar la misión
        if (canNinjaDoMission(selectedNinja, selectedMission)) {
            executeMission(selectedNinja, selectedMission);
        } else {
            System.out.println(selectedNinja.getName() + 
                             " no tiene el rango suficiente para esta misión.");
            System.out.println("Rango requerido: " + selectedMission.getRequiredRank());
            System.out.println("Rango del ninja: " + selectedNinja.getRank());
        }
    }
    
    private static Mission selectMission() {
        System.out.println("\nSelecciona la misión que deseas asignar:");
        System.out.println("════════════════════════════════════════");

        for (int i = 0; i < availableMissions.size(); i++) {
            Mission mission = availableMissions.get(i);
            System.out.printf("%d. %s\n", i + 1, mission.getName());
            System.out.printf("   Rango requerido: %s\n", mission.getRequiredRank());
            System.out.printf("   Recompensa: %d monedas\n\n", mission.getReward());
        }
        
        System.out.print("Elige una misión (1-" + availableMissions.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= availableMissions.size()) {
            return availableMissions.get(choice - 1);
        } else {
            System.out.println("Selección inválida.");
            return null;
        }
    }
    
    private static boolean canNinjaDoMission(Ninja ninja, Mission mission) {
        // Verificar si el rango del ninja es suficiente para la misión
        Rank ninjaRank = ninja.getRank();
        Rank requiredRank = mission.getRequiredRank();
        
        // Orden de rangos: GENIN < CHUNIN < JONIN
        return getRankValue(ninjaRank) >= getRankValue(requiredRank);
    }
    
    private static int getRankValue(Rank rank) {
        switch (rank) {
            case GENIN: return 1;
            case CHUNIN: return 2;
            case JONIN: return 3;
            default: return 0;
        }
    }
    
    private static void executeMission(Ninja ninja, Mission mission) {
        System.out.println("¡Misión asignada con éxito!");
        System.out.println("═══════════════════════════════");
        System.out.println("Ninja: " + ninja.getName());
        System.out.println("Misión: " + mission.getName());
        
        // Simular progreso de la misión
        System.out.println("\nEjecutando misión...");
        simulateMissionProgress();
        
        // Calcular probabilidad de éxito basada en las estadísticas del ninja
        boolean success = calculateMissionSuccess(ninja, mission);
        
        if (success) {
            System.out.println("\n¡MISIÓN COMPLETADA CON ÉXITO!");
            System.out.println("═══════════════════════════════════");
            System.out.println(ninja.getName() + " ha completado la misión: " + mission.getName());
            System.out.println("Recompensa obtenida: " + mission.getReward() + " monedas");
            
            // Dar experiencia adicional
            System.out.println("\nExperiencia ganada:");
            giveExperienceForMission(ninja, mission);
        } else {
            System.out.println("\nMISIÓN FALLIDA");
            System.out.println("══════════════════");
            System.out.println(ninja.getName() + " no pudo completar la misión: " + mission.getName());
            System.out.println("No se obtuvo recompensa, pero ganó algo de experiencia.");
            
            // Dar experiencia reducida por el intento
            giveFailureExperience(ninja);
        }
    }
    
    private static void simulateMissionProgress() {
        String[] progressMessages = {
            "Preparando equipo...",
            "Saliendo de la aldea...",
            "Llegando al destino...",
            "Ejecutando plan...",
            "Completando objetivos..."
        };
        
        try {
            for (String message : progressMessages) {
                System.out.println(message);
                Thread.sleep(800); // Pausa de 0.8 segundos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private static boolean calculateMissionSuccess(Ninja ninja, Mission mission) {
        // Calcular probabilidad basada en estadísticas del ninja
        int totalStats = ninja.getStats().getAttack() + 
                        ninja.getStats().getDefense() + 
                        ninja.getStats().getChakra();
        
        // Base de probabilidad según el rango de la misión
        double baseSuccessRate = switch (mission.getRequiredRank()) {
            case GENIN -> 0.8;
            case CHUNIN -> 0.6;
            case JONIN -> 0.4;
        };
        
        // Bonificación por estadísticas altas
        double statBonus = Math.min(totalStats / 1000.0, 0.3); // Máximo 30% de bonus
        
        double finalSuccessRate = baseSuccessRate + statBonus;
        finalSuccessRate = Math.min(finalSuccessRate, 0.95); // Máximo 95% de éxito
        
        Random random = new Random();
        return random.nextDouble() < finalSuccessRate;
    }
    
    private static void giveExperienceForMission(Ninja ninja, Mission mission) {
        int expGain = switch (mission.getRequiredRank()) {
            case GENIN -> 2;
            case CHUNIN -> 4;
            case JONIN -> 6;
        };
        
        // Aplicar entrenamiento básico múltiples veces para simular experiencia
        for (int i = 0; i < expGain; i++) {
            trainingService.basicTraining(ninja);
        }
        
        System.out.println("+" + expGain + " puntos de experiencia aplicados");
        showNinjaStats(ninja, "Estadísticas actualizadas");
    }
    
    private static void giveFailureExperience(Ninja ninja) {
        // Dar experiencia mínima por el intento
        trainingService.basicTraining(ninja);
        System.out.println("+1 punto de experiencia por el intento");
    }

    private static void startTrainingAndBattle() {
        System.out.println("\n¡Es hora de entrenar y combatir!");
        System.out.println("═══════════════════════════════════");
        
        // Mostrar ninjas disponibles
        Ninja selectedNinja = selectNinja();
        if (selectedNinja == null) return;
        
        showNinjaStats(selectedNinja, "Estadísticas iniciales");
        
        performTraining(selectedNinja);
        
        showNinjaStats(selectedNinja, "Estadísticas después del entrenamiento");
        
        Ninja opponent = selectRandomOpponent(selectedNinja);
        System.out.println("\n¡Tu oponente ha sido seleccionado aleatoriamente!");
        showNinjaStats(opponent, "Tu oponente");
        
        System.out.println("\n¡COMIENZA EL COMBATE!");
        System.out.println("═══════════════════════");
        
        Ninja winner = CombatSimulator.fight(selectedNinja, opponent);
        
        System.out.println("\n¡RESULTADO DEL COMBATE!");
        if (winner == selectedNinja) {
            System.out.println("¡FELICIDADES! " + selectedNinja.getName() + " ha ganado el combate!");
        } else {
            System.out.println(selectedNinja.getName() + " ha perdido contra " + opponent.getName());
        }
        
        askForReport(selectedNinja, opponent);
    }
    
    private static Ninja selectNinja() {
        System.out.println("\nSelecciona tu ninja:");
        System.out.println("══════════════════════");
        
        for (int i = 0; i < availableNinjas.size(); i++) {
            Ninja ninja = availableNinjas.get(i);
            System.out.printf("%d. %s (%s) - Aldea: %s - Rango: %s\n", 
                i + 1, ninja.getName(), ninja.getVillage(), 
                ninja.getVillage(), ninja.getRank());
        }
        
        System.out.print("\nElige tu ninja (1-" + availableNinjas.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= availableNinjas.size()) {
            return availableNinjas.get(choice - 1);
        } else {
            System.out.println("Selección inválida.");
            return null;
        }
    }
    
    private static void performTraining(Ninja ninja) {
        System.out.println("\n¿Qué tipo de entrenamiento deseas realizar?\n1. Entrenamiento básico (+1 ATK, +1 DEF, +2 CHK\n" + 
            "2. Entrenamiento avanzado (+3 ATK, +2 DEF, +5 CHK)\nSelecciona el tipo de entrenamiento: ");
        
        int trainingChoice = getIntInput();
        
        switch (trainingChoice) {
            case 1:
                trainingService.basicTraining(ninja);
                break;
            case 2:
                trainingService.advancedTraining(ninja);
                break;
            default:
                System.out.println("Opción inválida, realizando entrenamiento básico...");
                trainingService.basicTraining(ninja);
        }
    }
    
    private static Ninja selectRandomOpponent(Ninja selectedNinja) {
        List<Ninja> opponents = new ArrayList<>(availableNinjas);
        opponents.remove(selectedNinja);
        
        Random random = new Random();
        return opponents.get(random.nextInt(opponents.size()));
    }
    
    private static void showNinjaStats(Ninja ninja, String title) {
        System.out.println("\n" + title + ":");
        System.out.println("─────────────────────────────────");
        System.out.println("Nombre: " + ninja.getName());
        System.out.println("Aldea: " + ninja.getVillage());
        System.out.println("Rango: " + ninja.getRank());
        System.out.println("Ataque: " + ninja.getStats().getAttack());
        System.out.println("Defensa: " + ninja.getStats().getDefense());
        System.out.println("Chakra: " + ninja.getStats().getChakra());
        System.out.println("Poder total: " + calculatePower(ninja));
        
        if (!ninja.getJutsus().isEmpty()) {
            System.out.println("Jutsus:");
            ninja.getJutsus().forEach(jutsu -> 
                System.out.println("\t" + jutsu.getName() + " (Costo Chakra: " + jutsu.getChakraCost() + ")")
            );
        }
    }
    
    private static int calculatePower(Ninja ninja) {
        return ninja.getStats().getAttack() + ninja.getStats().getDefense() + ninja.getStats().getChakra() / 2;
    }
    
    private static void showAllNinjas() {
        System.out.println("\nTODOS LOS NINJAS DISPONIBLES");
        System.out.println("═══════════════════════════════");
        
        for (Ninja ninja : availableNinjas) {
            showNinjaStats(ninja, "");
            System.out.println();
        }
    }
    
    private static void askForReport(Ninja ninja1, Ninja ninja2) {
        System.out.print("\n¿Deseas ver el reporte detallado? (s/n): ");
        String response = scanner.nextLine().toLowerCase();
        
        if (response.equals("s") || response.equals("si") || response.equals("sí")) {
            generateReport(ninja1, ninja2);
        }
    }
    
    private static void generateReport(Ninja ninja1, Ninja ninja2) {
        System.out.println("\nREPORTE DETALLADO DEL COMBATE");
        System.out.println("═══════════════════════════════════");
        
        // Reporte en texto
        com.ninjaSystem.patterns.visitor.ExportVisitor textVisitor = 
            new com.ninjaSystem.patterns.visitor.TextExportVisitor();
        ninja1.accept(textVisitor);
        ninja2.accept(textVisitor);
        
        System.out.println("=== REPORTE EN TEXTO ===");
        System.out.println(textVisitor.getReport());
        
        // Reporte en JSON
        com.ninjaSystem.patterns.visitor.ExportVisitor jsonVisitor = 
            new com.ninjaSystem.patterns.visitor.JsonExportVisitor();
        ninja1.accept(jsonVisitor);
        ninja2.accept(jsonVisitor);
        
        System.out.println("\n=== REPORTE EN JSON ===");
        System.out.println(jsonVisitor.getReport());
    }
    
    private static int getIntInput() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.print("Por favor, introduce un número válido: ");
            return getIntInput();
        }
    }
}
