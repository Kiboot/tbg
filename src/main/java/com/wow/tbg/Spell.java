public class Spell {
    private String name;
    private int manaCost;
    private int effectValue;
    private String effectType; // "damage", "heal", "buff", "debuff"
    private int maxCooldown;
    private int currentCooldown; // Tracks remaining cooldown turns
    private String targetType; // "single", "multi", "self"

    public Spell(String name, int manaCost, int effectValue, String effectType, int maxCooldown, String targetType) {
        this.name = name;
        this.manaCost = manaCost;
        this.effectValue = effectValue;
        this.effectType = effectType;
        this.maxCooldown = maxCooldown;
        this.currentCooldown = 0; // Starts as available
        this.targetType = targetType;
    }

    // Getters
    public String getName() { return name; }
    public int getManaCost() { return manaCost; }
    public int getEffectValue() { return effectValue; }
    public String getEffectType() { return effectType; }
    public int getCurrentCooldown() { return currentCooldown; }
    public String getTargetType() { return targetType; }

    // Checks if the spell is available (not on cooldown)
    public boolean isAvailable() {
        return currentCooldown == 0;
    }

    // Resets cooldown after casting
    public void resetCooldown() {
        currentCooldown = maxCooldown;
    }

    // Reduces cooldown each turn (should be called per game turn)
    public void decrementCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    // Retrieves damage value if the spell type is "damage"
    public int getDamageValue() {
        return effectType.equalsIgnoreCase("damage") ? effectValue : 0;
    }

    // Spell Casting
    public void castSpell(Character caster, Character target) {
        if (!isAvailable()) {
            System.out.println(name + " is on cooldown for " + currentCooldown + " more turns!");
            return;
        }

        if (caster.getMana() < manaCost) {
            System.out.println(caster.getName() + " does not have enough mana to cast " + name + "!");
            return;
        }

        caster.useMana(manaCost);
        resetCooldown(); // Spell goes on cooldown after casting

        System.out.println(caster.getName() + " casts " + name + "!");

        switch (effectType.toLowerCase()) {
            case "damage":
                target.takeDamage(effectValue);
                System.out.println(target.getName() + " takes " + effectValue + " damage!");
                break;
            case "heal":
                caster.setHealth(caster.getHealth() + effectValue);
                System.out.println(caster.getName() + " heals for " + effectValue + " HP!");
                break;
            case "buff":
                applyBuff(caster);
                break;
            case "debuff":
                applyDebuff(target);
                break;
            default:
                System.out.println("Invalid spell type!");
        }
    }

    // Buff/Debuff Handling
    private void applyBuff(Character caster) {
        // Implement buff logic template here for armor
        caster.setArmor(caster.getArmor() + effectValue);
        System.out.println(caster.getName() + " gains +" + effectValue + " armor!");
    }

    private void applyDebuff(Character target) {
        target.setBaseSpeed(target.getBaseSpeed() - effectValue);
        System.out.println(target.getName() + "'s speed is reduced by " + effectValue + "!");
    }
}
