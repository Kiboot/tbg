public class DamageSpell extends Spell {
    private int damageValue;

    public DamageSpell(String name, int manaCost, int cooldown, int damageValue) {
        super(name, manaCost, cooldown);
        this.damageValue = damageValue;
    }

    public int getDamageValue() {
        return damageValue;
    }
}