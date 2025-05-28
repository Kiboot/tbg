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

    public void takeDamage(int damage) {
        final int BASE_VALUE = 100;
        int finalDamage = (int) (damage * (1 - (armor / (armor + BASE_VALUE))));
        health -= finalDamage; // **HP properly updates in memory**
        if (health < 0){health = 0;}  // Prevent negative HP
        System.out.println(name + " takes " + finalDamage + " damage! Health left: " + health);
    }

    public boolean isDefeated() {return health <= 0;}
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