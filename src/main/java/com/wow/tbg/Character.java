
package com.wow.tbg;
import java.util.Random;

public class Character {
    protected String name;
    protected int health;
    protected int minATK;
    protected int maxATK;
    protected double armor;
    protected double speed;

    public Character(String name, int health, int minATK, int maxATK, double armor, double speed) {
        this.name = name;
        this.health = health;
        this.minATK = minATK;
        this.maxATK = maxATK;
        this.armor = armor;
        this.speed = speed;
    }

    public void takeDamage(int incomingDamage) {
        final int BASE_VALUE = 100; // Balance factor for armor scaling
        int finalDamage = (int) (incomingDamage * (1 - (double) armor / (armor + BASE_VALUE)));
    }

    public boolean isDefeated() {
        return health <= 0;
    }
    public int getAttack() {
        Random rand = new Random();
        return rand.nextInt((maxATK - minATK) + 1) + minATK;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMinATK() { return minATK; }
    public int getMaxATK() { return maxATK; }
    public double getArmor() { return armor; }
    public double getSpeed() { return speed;}

    

}