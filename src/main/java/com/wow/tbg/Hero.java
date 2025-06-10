import java.util.Random;

public class Hero extends Character {
    private int constitution;   // Affects HP and regeneration rate
    private int strength;       // Boosts melee damage
    private int intelligence;   // Enhances magic damage and skill mastery
    private int agility;        // Speeds up turn order and improves ranged accuracy
    private int dexterity;      // Increases evasion rate and critical hit mechanics

    private int maxHealth;
    private int currentHealth;
    private int maxMana;
    private int currentMana;
    private boolean isMelee;
    private double accuracyRate = 1;
    private double evasion = 0;
    private double criticalChance = 0; // Critical hit chance
    private double baseXPRate = 1; // Base experience rate
    private double evasionRate = 0;
    private double carryWeight = 20; // Carrying capacity
    
    private int magicBonus = 0; // Magic damage bonus
    private int meleeBonus = 0; // Melee damage bonus
    private int rangedBonus = 0; // Ranged damage bonus
    private double criticalMult = 1.5; // Critical damage multiplier
    //private Spell[] spellBook;

    public Hero(String name, int baseHealth, int baseMana, int minATK, int maxATK, double armor, double baseSpeed,
                int constitution, int strength, int intelligence, int agility, int dexterity) {
        super(name, minATK, maxATK, armor, baseSpeed); // Call the constructor of the superclass Character

        this.constitution = constitution; //Directly affects total Health Points (HP) and natural regeneration rate.
        this.strength = strength;         //Increases Carry Weight capacity and boosts Melee Damage Bonus for physical attacks.
        this.intelligence = intelligence; //Grants an Experience Bonus for skill mastery, increases Ki Pool, and enhances Technique Damage Bonus
        this.agility = agility;     //Speeds up Turn Order/Initiative, affects Movement Speed, and improves Ranged Attack Accuracy for projectile combat.
        this.dexterity = dexterity; //Boosts Evasion Rate, increases Critical Damage, and raises Critical Hit Chance

        applyAttributeBonuses();
        this.currentHealth = this.maxHealth;
        this.currentMana = this.maxMana;      
    }


    private void applyAttributeBonuses() {
        this.maxHealth += calculateCONBonus();
        this.maxMana += calculateINTBonus();
        this.magicBonus += calculateINTBonus();
        this.baseSpeed += calculateAGIBonus();
        this.accuracyRate += calculateAGIBonus2();
        this.meleeBonus += calculateSTRBonus();
        this.carryWeight += calculateSTRBonus2();
        this.evasionRate += calculateDEXBonus();
        this.criticalChance += calculateDEXBonus();
        this.criticalMult += calculateDEXBonus();
    }

    // Method to calculate and apply attribute-based bonuses
    private int calculateCONBonus() {return constitution * 5;}
    private int calculateINTBonus() {return intelligence * 3;}
    private int calculateSTRBonus() {return strength * 2;}
    private int calculateSTRBonus2() {return strength * 5;}
    private double calculateAGIBonus() {return agility * 2;}
    private double calculateAGIBonus2() {return agility * .02;}
    private double calculateDEXBonus() {return dexterity *.01;}

    public double getSpeed() {
        return baseSpeed;
    }

    // --- Getters for Attributes ---
    public int getConstitution() { return constitution; }
    public int getStrength() { return strength; }
    public int getIntelligence() { return intelligence; }
    public int getAgility() { return agility; }
    public int getDexterity() { return dexterity; }

    // --- Getters for Stats ---
    public int getMaxHealth() { return maxHealth; }
    public int getHealth() { return currentHealth; }
    public int getMaxMana() { return maxMana; }
    public int getMana() { return currentMana; }

    // --- Health & Mana Management ---
    public void restoreHealth(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
        System.out.println("❤️ " + getName() + " recovers " + amount + " HP!");
    }
    public void restoreMana(int amount) {
        currentMana = Math.min(currentMana + amount, maxMana);
        System.out.println("💧 " + getName() + " regenerates " + amount + " MP!");
    }

    // --- Melee Attack: Strength-based ---
    public void executeMeleeAttack(Character target) {
        Random rand = new Random();
        int baseDamage = rand.nextInt((maxATK - minATK) + 1) + minATK;
        int finalDamage = baseDamage + this.meleeBonus;
        target.takeDamage(finalDamage);
        System.out.println("⚔️ " + getName() + " strikes " + ((Character) target).getName() + " for " + finalDamage + " melee damage!");
    }
    // --- Magic Attack: Intelligence-based ---
    public void castSpell(Spell spell, Character target) {
        if (!spell.isAvailable()) { //if spell is on cooldown
            System.out.println("⛔ " + spell.getName() + " is on cooldown! (" + spell.getCurrentCooldown() + " turns left)");
            return;}
        if (currentMana >= spell.getManaCost()) {
            restoreMana(-spell.getManaCost()); // Deduct MP
            spell.resetCooldown();
            int spellDamage = spell.getDamageValue() + this.magicBonus;
            System.out.println("🔥 " + getName() + " casts " + spell.getName() + ", dealing " + spellDamage + " magical damage!");
            target.takeDamage(spellDamage);
        } else {
            System.out.println("⛔ Not enough MP to cast " + spell.getName() + "!");}
    }

    // --- Ranged Attack: Agility-based ---
    public void executeRangedAttack(Character target) {
        Random rand = new Random();
        int baseDamage = rand.nextInt((maxATK - minATK) + 1) + minATK;
        int finalDamage = (int)(baseDamage + this.accuracyRate);
        target.takeDamage(finalDamage);
        System.out.println("🏹 " + getName() + " fires an arrow at " + ((Character) target).getName() + " for " + finalDamage + " damage!");
    }

    // --- Critical Hit Chance: Dexterity-based ---
    public boolean isCriticalHit() { return Math.random() < criticalChance;}

    // --- Evasion Mechanic: Dexterity-based ---
    public boolean evadesAttack() { return Math.random() < evasionRate;}

    public void triggerPassiveHitEffects(Character attacker, Character target) {
        
        if(isCriticalHit()){
            Random rand = new Random();
            int baseDamage = rand.nextInt((maxATK - minATK) + 1) + minATK;
            int finalDamage;
            if(isMelee){
                finalDamage = (int)((baseDamage + this.meleeBonus )* criticalMult);
            }
            else{
                finalDamage = (int)((baseDamage + this.accuracyRate) * criticalMult);
            }
            target.takeDamage(finalDamage);
            System.out.println(attacker.getName() + " lands a CRITICAL HIT!");
        }
    }

}
