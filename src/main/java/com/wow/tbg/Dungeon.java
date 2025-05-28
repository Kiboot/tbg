package com.wow.tbg;

public class Dungeon {
    private String name;
    private Monster[] monsterPool;
    private String[] lootPool;
    private int levels;
    private double monsterSpawnRate; // Stores monster spawn chance
    private double lootSpawnRate; // Stores loot spawn chance

    public Dungeon(String name, Monster[] monsters, String[] loot, int levels, double monsterRate, double lootRate) {
        this.name = name;
        this.monsterPool = monsters;
        this.lootPool = loot;
        this.levels = levels;
        this.monsterSpawnRate = monsterRate; // Store spawn rate instead of handling logic
        this.lootSpawnRate = lootRate;
    }

    public String getName() {return name;}
    public Monster getRandomMonster() {return monsterPool[(int) (Math.random() * monsterPool.length)];}
    public String getRandomLoot() {return lootPool[(int) (Math.random() * lootPool.length)];}
    public int getLevels() {return levels;}
    public double getMonsterSpawnRate() {return monsterSpawnRate;}
    public double getLootSpawnRate() {return lootSpawnRate;}
}

