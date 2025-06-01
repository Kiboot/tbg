
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static int playerGold = 200; // Starting gold
    private static Hero hero;

    public static void main(String[] args) {
        hero = selectHero(playerGold, false); // Let players choose their hero
        townHub(); // Start the town hub loop
    }
    public static Hero selectHero(int playerGold, boolean isReplacing) {
        int heroCost = 100; // Cost of purchasing a new hero

        if (isReplacing) {
            System.out.println("\n💰 Your hero has fallen! You need to recruit a new hero.");
            System.out.println("Buying a hero costs " + heroCost + " gold.");
            
            if (playerGold < heroCost) {
                System.out.println("⛔ Not enough gold! Game Over.");
                System.exit(0);
            }
            playerGold -= heroCost; // Deduct gold for new hero
        } else {
            System.out.println("\n🦸 Choose your starting hero!");
        }

        System.out.println("1. Warrior (High HP, Strong Attack)");
        System.out.println("2. Rogue (Fast, High Critical Damage)");
        System.out.println("3. Mage (Powerful Magic, Low Armor)");

        while (true) {
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                return new Hero(
                    "Warrior", 
                    150, 
                    20, 
                    35, 
                    12.0, 
                    10.0,
                    20,
                    new Spell[]{
                        new Spell("Fireball", 10, 5),
                        new Spell("Lightning Strike", 15, 6),
                        new Spell("Heal", 15, 6)});
            } else if (choice.equals("2")) {
                return new Hero(
                    "Rogue", 
                    100, 
                    15, 
                    40, 
                    8.0, 
                    20.0,
                    50,
                    new Spell[]{
                        new Spell("Poison Dart", 5, 20),
                        new Spell("Critical Strike", 10, 50),
                        new Spell("Stealth", 0, 0)
                    });
            } else if (choice.equals("3")) {
                return new Hero(
                    "Mage", 
                    80, 
                    25, 
                    50, 
                    5.0, 
                    12.0,
                    100,
                    new Spell[]{
                        new Spell("Fireball", 10, 5),
                        new Spell("Lightning Strike", 15, 4),
                        new Spell("Heal", 5, 3)
                    });
            } else {
                System.out.println("⛔ Invalid choice! Try again.");
            }
        }
    }

    public static Map<String, Dungeon> initializeDungeons() {
        Map<String, Dungeon> dungeons = new HashMap<>();
        // Initialize dungeons in a method to make the code inside the main method more readable
        dungeons.put("Crypt", new Dungeon(
            "Crypt",        // Dungeon name
            5,            // Number of levels/floors
            0.4,     // Monster spawn rate
            0.2,        // Loot spawn rate
            0,               // Entry fee
            new Monster[]{
                new Monster(
                    "Skeleton",     // Monster name
                    60,           // Monster health
                    10,           // Monster min attack
                    20,           // Monster max attack
                    8.0,           // Monster armor
                    5.5),          // Monster speed
                new Monster(
                    "Zombie", 
                    80, 
                    12, 
                    15, 
                    6.0, 
                    4.0)
                },
            new String[]{               // Loot pool, unused at the moment
                "Ancient Coin", 
                "Cursed Amulet"
            })); // Free entry

        dungeons.put("Cave", new Dungeon(
            "Cave",
            6, 
            0.5, 
            0.3, 
            50, 
            new Monster[]{
                new Monster(
                    "Goblin", 
                    70, 
                    14, 
                    18, 
                    7.5, 
                    6.5),
                new Monster(
                    "Troll", 
                    120, 
                    18, 
                    25, 
                    9.5, 
                    3.5)
                },
            new String[]{"Gold Nugget", "Rare Gem"})); // Costs 50 gold
        return dungeons;
    }


    private static void townHub() {

        Map<String, Dungeon> dungeons = initializeDungeons();
        Dungeon crypt = dungeons.get("Crypt");
        Dungeon cave = dungeons.get("Cave");

        while (true) {
            System.out.println("\n🌆 Welcome to town! What would you like to do?");
            System.out.println("1. Enter Crypt (5 Levels) - 💰 Cost: " + crypt.getEntryFee());
            System.out.println("2. Enter Cave (6 Levels) - 💰 Cost: " + cave.getEntryFee());
            System.out.println("3. View Gold (" + playerGold + " gold)");
            System.out.println("X. Exit Program");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("1")){
                playerGold = enterDungeon(hero, dungeons.get(crypt.getName()), playerGold);}  
            else if (choice.equals("2")){
                playerGold = enterDungeon(hero, dungeons.get(cave.getName()), playerGold);}
            else if (choice.equals("3")) System.out.println("💰 You have " + playerGold + " gold.");
            else if (choice.equals("X")) {
                System.out.println("👋 Thanks for playing! Exiting game...");
                System.exit(0);
            } else {
                System.out.println("⛔ Invalid choice! Try again.");
            }
        }
    }

    private static int enterDungeon(Hero hero, Dungeon dungeon, int playerGold) {
        //changed enterDungeon method type from void to int to return playerGold
        if (playerGold >= dungeon.getEntryFee()) {
            playerGold -= dungeon.getEntryFee(); // Deduct entry fee
            System.out.println("🏰 Entering " + dungeon.getName() + "... (-" + dungeon.getEntryFee() + " gold)");

            playerGold = startDungeon(hero, dungeon, playerGold); // Update gold after dungeon run

            if (hero.isDefeated()) hero = selectHero(playerGold, true); // Offer hero purchase if defeated
        } else {
            System.out.println("⛔ Not enough gold!");
        }
        return playerGold; // Return updated gold amount
    }

    public static int startDungeon(Hero hero, Dungeon dungeon, int playerGold) {
        System.out.println("🏰 You enter the " + dungeon.getName() + " dungeon...");

        for (int level = 1; level <= dungeon.getLevels(); level++) {
            System.out.println("\n🚶 Advancing to Level " + level);

            boolean battleComplete = false;

            while (!battleComplete) {
                System.out.println("\n🔹 Current HP = " + hero.getHealth() + " | Gold = " + playerGold);
                System.out.print("What do you want to do? (proceed, exit, inventory): ");
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.equals("exit")) {
                    System.out.println("🚪 You exit the dungeon.");
                    return playerGold;
                } else if (choice.equals("inventory")) {
                    System.out.println("🎒 Managing inventory... (Feature coming soon!)");
                } else if (choice.equals("proceed")) {
                    battleComplete = false; // Reset before each new encounter

                    if (Math.random() < dungeon.getMonsterSpawnRate()) {
                        Monster monster = dungeon.getRandomMonster();
                        System.out.println("🔥 A " + monster.getName() + " appeared!");
                        playerGold = startBattle(hero, monster, dungeon, playerGold);
                        if (hero.isDefeated()) return playerGold;
                    }

                    if (Math.random() < dungeon.getLootSpawnRate()) {
                        System.out.println("💰 You found " + dungeon.getRandomLoot() + "!");
                    }
                    battleComplete = true;
                } else {
                    System.out.println("⛔ Invalid choice! Try again.");
                }
            }
        }
        System.out.println("\n🏆 You completed the " + dungeon.getName() + "!");
        return playerGold;
    }


    public static int startBattle(Hero hero, Monster monster, Dungeon dungeon, int playerGold) {
        System.out.println("\n⚔️ Battle starts between " + hero.getName() + " and " + monster.getName());

            while (!hero.isDefeated() && !monster.isDefeated()) {
                System.out.println("\n🔹 " + hero.getName() + " HP: " + hero.getHealth() + " | MP: " + hero.getMana());
                System.out.println("🆚 " + monster.getName() + " HP: " + monster.getHealth());

                showCombatOptions(hero); // Displays available actions dynamically

                System.out.print("> ");
                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    executeAttack(hero,monster);
                } else {
                    try {
                        int spellChoice = Integer.parseInt(choice) - 2; // Adjust index to match spell selection
                        Spell[] spells = hero.getSpells();

                        if (spellChoice >= 0 && spellChoice < spells.length) {
                            hero.castSpell(spells[spellChoice], monster);
                        } else {
                            System.out.println("⛔ Invalid choice! Try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⛔ Invalid input! Try again.");
                    }
                }

                if (!monster.isDefeated()) {
                    executeAttack(monster,hero);
                }
            }
        return endBattle(hero, monster, dungeon, playerGold); // Ensure playerGold is updated
    }
    public static void playerTurn(Hero hero, Monster monster) {
        System.out.println("\n🎮 Your turn! Choose an action:");
        System.out.println("Type: 'attack', 'defend', or 'item'");

        String choice = scanner.nextLine();

        switch (choice.toLowerCase()) {
            case "attack":
                executeAttack(hero, monster);
                break;
            case "defend":
                System.out.println(hero.getName() + " braces for impact, reducing damage taken!");
                // hero.reduceDamage();
                break;
            case "item":
                System.out.println("🎒 Inventory feature coming soon!");
                break;
            default:
                System.out.println("⛔ Invalid choice! You lose your turn.");
        }
    }

    private static void executeAttack(Character attacker, Character defender) {
        int attackValue = attacker.getAttack(); 
        int finalDamage = (int) (attackValue * (1 - (defender.getArmor() / (defender.getArmor() + 100))));

        defender.takeDamage(finalDamage); 

        System.out.println(attacker.getName() + " attacks " + defender.getName() + " for " + attackValue + " damage! (Final Damage: " + finalDamage + ")");
        // Display HP after each attack
        System.out.println("----------------------------------------------");
        System.out.println("🔹 " + attacker.getName() + " HP: " + attacker.getHealth());
        System.out.println("🔹 " + defender.getName() + " HP: " + defender.getHealth());
        System.out.println("----------------------------------------------");
    }
    

    private static int endBattle(Hero hero, Monster monster, Dungeon dungeon, int playerGold) {
        if (hero.isDefeated()) {
            System.out.println("💀 " + hero.getName() + " has fallen! You must recruit a new hero.");
            hero = selectHero(playerGold, true); // Buy a new hero
            return playerGold;
        }
        else {
            System.out.println("🏆 " + hero.getName() + " defeated " + monster.getName() + "!");

            // Gold Drop Calculation (1/3 of dungeon entry fee ± random variation)
            int baseGoldDrop = dungeon.getEntryFee() / 3;
            int variance = (int) (Math.random() * 5 - 2); // ±2 gold randomness
            int goldEarned = Math.max(1, baseGoldDrop + variance); // Ensure at least 1 gold

            playerGold += goldEarned;
            System.out.println("💰 You earned " + goldEarned + " gold!");
            return playerGold; // Return updated gold amount
        }
    }

    // Handles casting a spell, ensuring cooldown and mana costs are checked
    public static void castSpell(Hero hero, Monster monster, Spell spell) {
        if (!spell.isAvailable()) {
            System.out.println("⛔ " + spell.getName() + " is on cooldown! (" + spell.getCurrentCooldown() + " turns left)");
            return;
        }

        if (hero.getMana() >= spell.getManaCost()) {
            hero.setMana(hero.getMana() - spell.getManaCost()); // Deduct MP
            spell.resetCooldown(); // Starts cooldown

            System.out.println("🔥 " + hero.getName() + " casts " + spell.getName() + "!");

            // Effects are applied dynamically based on spell context
        } else {
            System.out.println("⛔ Not enough MP to cast " + spell.getName() + "!");
        }
    }

// Called at the end of each turn to reduce spell cooldowns
    public static void reduceSpellCooldowns(Hero hero) {
        for (Spell spell : hero.getSpells()) {
            spell.reduceCooldown();
        }
    }

    public static void showCombatOptions(Hero hero) {
        System.out.println("\n🔹 Choose your action:");
        System.out.println("1. Attack");

        // Dynamically list hero's spells
        Spell[] spells = hero.getSpells();
        for (int i = 0; i < spells.length; i++) {
            System.out.println((i + 2) + ". " + spells[i]); // Spells start at index 2
        }

        // Placeholder for future item functionality
        System.out.println((spells.length + 2) + ". Items (Coming Soon)");
    }



}
