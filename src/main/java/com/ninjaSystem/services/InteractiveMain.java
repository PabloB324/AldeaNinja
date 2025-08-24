package com.ninjaSystem.services;

import com.ninjaSystem.domain.Ninja;
import com.ninjaSystem.domain.Rank;
import com.ninjaSystem.patterns.factory.*;

import java.util.*;

public class InteractiveMain {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Ninja> availableNinjas = new ArrayList<>();
    private static TrainingService trainingService = new TrainingService();
    
    public static void main(String[] args) {
        System.out.println("--- ¡Bienvenido al Sistema de Entrenamiento y Combate Ninja! ---");
        System.out.println("═══════════════════════════════════════════════════════════════");
        
        initializeNinjas();
        
        boolean continueGame = true;
        while (continueGame) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    startTrainingAndBattle();
                    break;
                case 2:
                    showAllNinjas();
                    break;
                case 3:
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
    
    private static void showMainMenu() {
        System.out.println("\n═══ MENÚ PRINCIPAL ═══");
        System.out.println("1. Entrenar ninja y combatir");
        System.out.println("2. Ver todos los ninjas disponibles");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }
    
    private static void startTrainingAndBattle() {
        System.out.println("\n¡Es hora de entrenar y combatir!");
        System.out.println("═══════════════════════════════════");
        
        // Mostrar ninjas disponibles
        Ninja selectedNinja = selectNinja();
        if (selectedNinja == null) return;
        
        // Mostrar estadísticas iniciales
        showNinjaStats(selectedNinja, "Estadísticas iniciales");
        
        // Preguntar tipo de entrenamiento
        performTraining(selectedNinja);
        
        // Mostrar estadísticas después del entrenamiento
        showNinjaStats(selectedNinja, "Estadísticas después del entrenamiento");
        
        // Seleccionar oponente aleatorio
        Ninja opponent = selectRandomOpponent(selectedNinja);
        System.out.println("\n¡Tu oponente ha sido seleccionado aleatoriamente!");
        showNinjaStats(opponent, "Tu oponente");
        
        // Simular combate
        System.out.println("\n¡COMIENZA EL COMBATE!");
        System.out.println("═══════════════════════");
        
        Ninja winner = CombatSimulator.fight(selectedNinja, opponent);
        
        System.out.println("\n¡RESULTADO DEL COMBATE!");
        if (winner == selectedNinja) {
            System.out.println("¡FELICIDADES! " + selectedNinja.getName() + " ha ganado el combate!");
        } else {
            System.out.println(selectedNinja.getName() + " ha perdido contra " + opponent.getName());
        }
        
        // Preguntar si quiere ver el reporte
        askForReport(selectedNinja, opponent);
    }
    
    private static Ninja selectNinja() {
        System.out.println("\nSelecciona tu ninja para entrenar:");
        System.out.println("═══════════════════════════════════");
        
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
        System.out.println("\n¿Qué tipo de entrenamiento deseas realizar?");
        System.out.println("1. Entrenamiento básico (+1 ATK, +1 DEF, +2 CHK)");
        System.out.println("2. Entrenamiento avanzado (+3 ATK, +2 DEF, +5 CHK)");
        System.out.print("Selecciona el tipo de entrenamiento: ");
        
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
        opponents.remove(selectedNinja); // Quitar el ninja seleccionado de los oponentes
        
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
