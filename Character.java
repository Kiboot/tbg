import java.util.Random;

public class Character {
    
    protected String name;
    protected int health;
    protected int mana;
    protected int minATK;
    protected int maxATK;
    protected double armor;
    protected double baseSpeed;

    public Character() {} // Default/Empty constructor
    public Character(String name) { this.name = name; } // Overloaded constructor that only takes a name

    // Complete Constructor
    public Character(String name, int health, int mana, int minATK, int maxATK, double armor, double baseSpeed) {
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.minATK = minATK;
        this.maxATK = maxATK;
        this.armor = armor;
        this.baseSpeed = baseSpeed;
    }

    public void takeDamage(int incomingDamage) {
        //function to take damage that can be reduced by armor
        double armorEffectiveness = 1 - (armor / (armor + 40.0));
        int reducedDamage = (int) (incomingDamage * armorEffectiveness);

        this.health -= reducedDamage;
        System.out.println(name + " took " + reducedDamage + " damage (Armor blocked " + (incomingDamage - reducedDamage) + ")!");

        if (health <= 0) {
            System.out.println(name + " has been defeated!");
        }
    }
    public void attack(Character target) {
        //function to attack
        Random rand = new Random();
        int damage = rand.nextInt((maxATK - minATK) + 1) + minATK;
        target.takeDamage(damage);
    }


    public boolean isAlive(){return this.health > 0;}
    public void useMana(int amount) {
        //function to use mana
        if (mana >= amount) {
            mana -= amount;
            System.out.println(name + " used " + amount + " mana!");
        } else {
            System.out.println(name + " does not have enough mana!");
        }
    }
    public void regenerateMana(int amount) {
        //function to regenerate mana
        mana += amount;
        System.out.println(name + " regained " + amount + " mana!");
    }

    public int getAttack() {
        //function to get randomized attack value
        Random rand = new Random();
        return rand.nextInt((maxATK - minATK) + 1) + minATK;
    }

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMana() { return mana; }
    public int getMinATK() { return minATK; }
    public int getMaxATK() { return maxATK; }
    public double getArmor() { return armor; }
    public double getBaseSpeed() { return baseSpeed; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setHealth(int health) { this.health = health; }
    public void setBaseSpeed(double baseSpeed) { this.baseSpeed = baseSpeed; }
    public void setMana(int mana) { this.mana = mana; }
    public void setArmor(double armor) { this.armor = armor; }
    public void setMinATK(int minATK) { this.minATK = minATK; }
    public void setMaxATK(int maxATK) { this.maxATK = maxATK; }
}
 