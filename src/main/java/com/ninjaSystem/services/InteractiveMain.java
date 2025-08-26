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
                    createNewNinja();
                    break;
                case 5:
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

        AbstractNinjaFactory konoha = new KonohaFactory();
        AbstractNinjaFactory suna = new SunaFactory();
        AbstractNinjaFactory kiri = new KiriFactory();
        AbstractNinjaFactory iwa = new IwaFactory();
        AbstractNinjaFactory kumo = new KumoFactory();

        availableNinjas.add(konoha.createNinja("Naruto", Rank.GENIN));
        availableNinjas.add(konoha.createNinja("Sasuke", Rank.GENIN));
        availableNinjas.add(konoha.createNinja("Kakashi", Rank.JONIN));
        availableNinjas.add(suna.createNinja("Gaara", Rank.CHUNIN));
    }

    private static void initializeMissions() {
        System.out.println("Inicializando misiones disponibles...\n");

        availableMissions.add(new Mission("Rescate del Gato Perdido", MissionRank.D, 100, Rank.GENIN));
        availableMissions.add(new Mission("Escolta de Mercaderes", MissionRank.C, 300, Rank.CHUNIN));
        availableMissions.add(new Mission("Infiltración Enemiga", MissionRank.A, 800, Rank.JONIN));
        availableMissions.add(new Mission("Caza de Bandidos", MissionRank.B, 500, Rank.CHUNIN));
        availableMissions.add(new Mission("Protección del Hokage", MissionRank.S, 1000, Rank.JONIN));
        availableMissions.add(new Mission("Entrega de Mensaje", MissionRank.D, 150, Rank.GENIN));
    }

    private static void showMenu() {
        System.out.println("\n═══ MENÚ PRINCIPAL ═══\n1. Entrenar ninja y combatir\n2. Ver todos los ninjas disponibles\n" +
                "3. Enviar ninja a mision\n4. Crear nuevo ninja\n5. Salir\nSelecciona una opción: ");
    }

    private static void selectMissionForNinja() {
        System.out.println("\n¡Es hora de enviar un ninja a una misión!");
        System.out.println("═══════════════════════════════════════");

        Ninja selectedNinja = selectNinja();
        if (selectedNinja == null) return;

        System.out.println("\nNinja seleccionado: " + selectedNinja.getName() +
                " (Rango: " + selectedNinja.getRank() + ")");

        Mission selectedMission = selectMission();
        if (selectedMission == null) return;

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
        Rank ninjaRank = ninja.getRank();
        Rank requiredRank = mission.getRequiredRank();

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

        System.out.println("\nEjecutando misión...");

        boolean success = calculateMissionSuccess(ninja, mission);

        if (success) {
            System.out.println("\n¡MISIÓN COMPLETADA CON ÉXITO!");
            System.out.println("═══════════════════════════════════");
            System.out.println(ninja.getName() + " ha completado la misión: " + mission.getName());
            System.out.println("Recompensa obtenida: " + mission.getReward() + " monedas");

            System.out.println("\nExperiencia ganada:");
            giveExperienceForMission(ninja, mission);
        } else {
            System.out.println("\nMISIÓN FALLIDA");
            System.out.println("══════════════════");
            System.out.println(ninja.getName() + " no pudo completar la misión: " + mission.getName());
            System.out.println("No se obtuvo recompensa, pero ganó algo de experiencia.");

            giveFailureExperience(ninja);
        }
        askForReportMission(ninja, mission);
    }

    private static boolean calculateMissionSuccess(Ninja ninja, Mission mission) {
        int totalStats = ninja.getStats().getAttack() +
                ninja.getStats().getDefense() +
                ninja.getStats().getChakra();

        double baseSuccessRate = switch (mission.getRequiredRank()) {
            case GENIN -> 0.8;
            case CHUNIN -> 0.6;
            case JONIN -> 0.4;
        };

        double statBonus = Math.min(totalStats / 1000.0, 0.3);

        double finalSuccessRate = baseSuccessRate + statBonus;
        finalSuccessRate = Math.min(finalSuccessRate, 0.95);

        Random random = new Random();
        return random.nextDouble() < finalSuccessRate;
    }

    private static void giveExperienceForMission(Ninja ninja, Mission mission) {
        int expGain = switch (mission.getRequiredRank()) {
            case GENIN -> 2;
            case CHUNIN -> 4;
            case JONIN -> 6;
        };

        for (int i = 0; i < expGain; i++) {
            trainingService.basicTraining(ninja);
        }

        System.out.println("+" + expGain + " puntos de experiencia aplicados");
        showNinjaStats(ninja, "Estadísticas actualizadas");
    }

    private static void giveFailureExperience(Ninja ninja) {
        trainingService.basicTraining(ninja);
        System.out.println("+1 punto de experiencia por el intento");
    }

    private static void startTrainingAndBattle() {
        System.out.println("\n¡Es hora de entrenar y combatir!");
        System.out.println("═══════════════════════════════════");

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

    private static void askForReportMission (Ninja ninja, Mission mision) {
        System.out.print("\n¿Deseas ver el reporte detallado? (s/n): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("s") || response.equals("si") || response.equals("sí")) {
            generateReportMission(ninja, mision);
        }
    }

    private static void generateReportMission (Ninja ninja, Mission mision) {
        System.out.println("\nREPORTE DETALLADO DE LA MISION");
        System.out.println("════════════════════════════════");

        com.ninjaSystem.patterns.visitor.ExportVisitor textVisitor =
                new com.ninjaSystem.patterns.visitor.TextExportVisitor();
        ninja.accept(textVisitor);
        mision.accept(textVisitor);

        System.out.println("=== REPORTE EN TEXTO ===");
        System.out.println(textVisitor.getReport());

        com.ninjaSystem.patterns.visitor.ExportVisitor jsonVisitor =
                new com.ninjaSystem.patterns.visitor.JsonExportVisitor();
        ninja.accept(jsonVisitor);
        mision.accept(jsonVisitor);

        System.out.println("\n=== REPORTE EN JSON ===");
        System.out.println(jsonVisitor.getReport());
    }

    private static void generateReport(Ninja ninja1, Ninja ninja2) {
        System.out.println("\nREPORTE DETALLADO DEL COMBATE");
        System.out.println("═══════════════════════════════════");

        com.ninjaSystem.patterns.visitor.ExportVisitor textVisitor =
                new com.ninjaSystem.patterns.visitor.TextExportVisitor();
        ninja1.accept(textVisitor);
        ninja2.accept(textVisitor);

        System.out.println("=== REPORTE EN TEXTO ===");
        System.out.println(textVisitor.getReport());

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

    private static void createNewNinja() {
        System.out.println("\n¡Crear un nuevo ninja!");
        System.out.println("═══════════════════════");

        System.out.print("Introduce el nombre del ninja: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        boolean nameExists = availableNinjas.stream()
                .anyMatch(ninja -> ninja.getName().equalsIgnoreCase(name));

        if (nameExists) {
            System.out.println("Ya existe un ninja con ese nombre. Por favor, elige otro nombre.");
            return;
        }

        String selectedVillage = selectVillage();
        if (selectedVillage == null) return;

        Rank selectedRank = selectRank();
        if (selectedRank == null) return;

        AbstractNinjaFactory factory = getFactoryForVillage(selectedVillage);
        Ninja newNinja = factory.createNinja(name, selectedRank);

        availableNinjas.add(newNinja);

        System.out.println("\n¡Ninja creado exitosamente!");
        System.out.println("═══════════════════════════════");
        showNinjaStats(newNinja, "Nuevo ninja creado");
    }

    private static String selectVillage() {
        System.out.println("\nSelecciona la aldea del ninja:");
        System.out.println("══════════════════════════════");
        System.out.println("1. Konoha (Aldea de la Hoja)\n2. Suna (Aldea de la Arena)\n3. Kiri (Aldea de la Niebla)\n" +
                "4. Iwa (Aldea de la Roca)\n5. Kumo (Aldea de la Nube)");

        System.out.print("\nElige la aldea (1-5): ");
        int choice = getIntInput();

        switch (choice) {
            case 1: return "Konoha";
            case 2: return "Suna";
            case 3: return "Kiri";
            case 4: return "Iwa";
            case 5: return "Kumo";
            default:
                System.out.println("Selección inválida.");
                return null;
        }
    }

    private static Rank selectRank() {
        System.out.println("\nSelecciona el rango del ninja:");
        System.out.println("══════════════════════════════");
        System.out.println("1. Genin (Rango básico)\n2. Chunin (Rango intermedio)\n3. Jonin (Rango avanzado)");

        System.out.print("\nElige el rango (1-3): ");
        int choice = getIntInput();

        switch (choice) {
            case 1: return Rank.GENIN;
            case 2: return Rank.CHUNIN;
            case 3: return Rank.JONIN;
            default:
                System.out.println("Selección inválida.");
                return null;
        }
    }

    private static AbstractNinjaFactory getFactoryForVillage(String village) {
        switch (village) {
            case "Konoha": return new KonohaFactory();
            case "Suna": return new SunaFactory();
            case "Kiri": return new KiriFactory();
            case "Iwa": return new IwaFactory();
            case "Kumo": return new KumoFactory();
            default: throw new IllegalArgumentException("Aldea no válida: " + village);
        }
    }
}