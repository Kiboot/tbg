public class Spell {
    private String name;
    private int manaCost;
    private int cooldown;  // Number of turns before reuse
    private int currentCooldown; // Tracks how many turns remain before reuse

    public Spell(String name, int manaCost, int cooldown) {
        this.name = name;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.currentCooldown = 0; // Starts available
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void resetCooldown() {
        currentCooldown = cooldown; // Resets cooldown to max
    }

    public void reduceCooldown() {
        if (currentCooldown > 0) currentCooldown--; // Decrease cooldown each turn
    }

    public boolean isAvailable() {
        return currentCooldown == 0; // Spell is usable if cooldown is 0
    }

    @Override
    public String toString() {
        return name + " (Cost: " + manaCost + " MP, Cooldown: " + cooldown + " turns)";
    }
}
