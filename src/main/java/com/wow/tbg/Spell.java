

public class Spell {
    private String name;
    private int manaCost;
    private int effectValue; // Damage OR healing amount
    private String effectType; // "Burn", "Freeze", "Heal", etc.
    private String targetType; // "Self" or "Enemy"

    public Spell(String name, int manaCost, int effectValue, String effectType, String targetType) {
        this.name = name;
        this.manaCost = manaCost;
        this.effectValue = effectValue;
        this.effectType = effectType;
        this.targetType = targetType;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public String getEffectType() {
        return effectType;
    }

    public String getTargetType() {
        return targetType;
    }

    @Override
    public String toString() {
        return name + " (" + effectType + ", Cost: " + manaCost + " MP, Target: " + targetType + ")";
    }
}

