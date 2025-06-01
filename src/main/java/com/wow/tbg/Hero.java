public class Hero extends Character {
    private int maxMana;
    private int currentMana;
    private Spell[] spellBook;

    public Hero(String name, int health, int minATK, int maxATK, double armor, double speed, int maxMana, Spell[] spells) {
        super(name, health, minATK, maxATK, armor, speed);
        this.maxMana = maxMana;
        this.currentMana = maxMana; // Hero starts with full mana
        this.spellBook = spells;
    }

    // --- Health Management ---
    public int getMaxHealth() {
        return super.getHealth(); // Assuming Character class handles max HP
    }

    public void setHealth(int health) {
        this.setHealth((Math.min(health, getMaxHealth()))); // Prevents overhealing
    }

    public void restoreHealth(int amount) {
        setHealth(getHealth() + amount);
        System.out.println("❤️ " + getName() + " recovers " + amount + " HP!");
    }

    // --- Getters ---
    public int getMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public Spell[] getSpells() {
        return spellBook;
    }

    public boolean isOutOfMana() {
        return currentMana <= 0;
    }

    // --- Setters ---
    public void setMaxMana(int maxMana) {
        this.maxMana = Math.max(0, maxMana); // Prevents negative mana values
    }

    public void setMana(int amount) {
        this.currentMana = Math.min(amount, maxMana); // Ensures it doesn't exceed maxMana
    }

    public void useMana(int amount) {
        if (currentMana >= amount) {
            currentMana -= amount; // Deducts the correct amount of MP
        } else {
            System.out.println("⛔ Not enough MP!");
        }
    }

    public void setSpells(Spell[] spells) {
        this.spellBook = spells; // Allows dynamic reassignment of spellbook
    }

    // --- Spell Casting ---
    public void castSpell(Spell spell, Monster monster) {
        if (!spell.isAvailable()) {
            System.out.println("⛔ " + spell.getName() + " is on cooldown! (" + spell.getCurrentCooldown() + " turns left)");
            return;
        }

        if (currentMana >= spell.getManaCost()) {
            useMana(spell.getManaCost()); // Deduct MP
            spell.resetCooldown(); // Start cooldown

            System.out.println("🔥 " + getName() + " casts " + spell.getName() + "!");

            if (spell instanceof HealingSpell) {
                ((HealingSpell) spell).applyHealing(this);
            } else if (spell instanceof DamageSpell) {
                monster.takeDamage(((DamageSpell) spell).getDamageValue());
            }
        } else {
            System.out.println("⛔ Not enough MP to cast " + spell.getName() + "!");
        }
    }
    // --- Cooldown Handling ---
    public void reduceSpellCooldowns() {
        for (Spell spell : spellBook) {
            spell.reduceCooldown();
        }
    }
    // --- Display Hero's Spell List ---
    public void displaySpells() {
        System.out.println("✨ " + getName() + "'s Spellbook:");
        for (Spell spell : spellBook) {
            System.out.println("🔮 " + spell);
        }
    }
}
