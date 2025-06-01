public class HealingSpell extends Spell {
    private int healingAmount;

    public HealingSpell(String name, int manaCost, int cooldown, int healingAmount) {
        super(name, manaCost, cooldown);
        this.healingAmount = healingAmount;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void applyHealing(Hero hero) {
        hero.restoreHealth(healingAmount);
        System.out.println("✨ " + hero.getName() + " heals for " + healingAmount + " HP using " + getName() + "!");
    }
}
