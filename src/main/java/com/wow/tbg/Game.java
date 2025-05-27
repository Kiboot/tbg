package com.wow.tbg;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final double ENEMY_ENCOUNTER_CHANCE = 0.4;
    private static final double LOOT_CHANCE = 0.2;
    private static Random rand = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void startDungeon(Hero hero) {
    System.out.println("🏰 You enter a dark dungeon...");
    int dungeonLength = rand.nextInt(2) + 5; // Randomly 5 or 6 paces

    for (int pace = 1; pace <= dungeonLength; pace++) {
        System.out.println("\n🚶 Moving to pace " + pace + "/" + dungeonLength);

        double eventRoll = rand.nextDouble();

        if (eventRoll < ENEMY_ENCOUNTER_CHANCE) {
            System.out.println("🔥 A monster appeared!");
            Monster monster = new Monster("Goblin", 80, 10, 25, 5.2, 8.6);
            
            if (!startBattle(hero, monster)) {
                return; // If hero dies, exit dungeon
            }

        } else if (eventRoll < ENEMY_ENCOUNTER_CHANCE + LOOT_CHANCE) {
            System.out.println("💰 You found loot!");
        } else {
            System.out.println("🔍 Nothing happens...");
        }

        // **Ask for next action**
        while (true) {
            System.out.print("\nWhat do you want to do? (proceed, exit, inventory): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("proceed")) {
                break; // Move to next pace
            } else if (choice.equalsIgnoreCase("exit")) {
                System.out.println("🚪 You exit the dungeon.");
                return;
            } else if (choice.equalsIgnoreCase("inventory")) {
                System.out.println("🎒 Managing inventory... (Feature coming soon!)");
            } else {
                System.out.println("⛔ Invalid choice! Try again.");
            }
        }
    }

    System.out.println("\n🏆 You completed the dungeon!");
}


    public static boolean startBattle(Hero hero, Monster monster) {
    System.out.println("\n⚔️ Battle starts between " + hero.getName() + " and " + monster.getName());

    while (!hero.isDefeated() && !monster.isDefeated()) {
        if (hero.getSpeed() >= monster.getSpeed()) {
            executeAttack(hero, monster);
            if (!monster.isDefeated()) executeAttack(monster, hero);
        } else {
            executeAttack(monster, hero);
            if (!hero.isDefeated()) executeAttack(hero, monster);
        }
    }

    if (hero.isDefeated()) {
        System.out.println("💀 " + hero.getName() + " was defeated! Game Over.");
        return false; // Hero died, exit game
    } else {
        System.out.println("🏆 " + hero.getName() + " defeated " + monster.getName() + "!");
        return true; // Hero won, continue dungeon exploration
    }
}

    private static void executeAttack(Character attacker, Character defender) {
        int attackValue = attacker.getAttack();
        int finalDamage = (int) (attackValue * (1 - (defender.getArmor() / (defender.getArmor() + 50.0))));

        defender.takeDamage(finalDamage);
        System.out.println(attacker.getName() + " attacks " + defender.getName() + " for " + attackValue + " damage (Final Damage: " + finalDamage + ")");
    }

    public static void main(String[] args) {
        Hero hero = new Hero("Arthur", 100, 15, 30, 10.5, 12.8);

        System.out.println("\n🌆 Welcome to town!");
        System.out.print("🏰 Enter the dungeon? (yes/no): ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("yes")) {
            startDungeon(hero);
        } else {
            System.out.println("🛑 You stayed in town. Game Over!");
        }
    }
}
