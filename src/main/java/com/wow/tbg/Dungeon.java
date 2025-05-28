package com.wow.tbg;

public class Dungeon {
    private String name;
    private Monster[] monsterPool;
    private String[] lootPool;
    private int levels;
    private double monsterSpawnRate;
    private double lootSpawnRate;
    private int entryFee; // 💰 New field for entry cost

    public Dungeon(String name, Monster[] monsters, String[] loot, int levels, double monsterRate, double lootRate, int fee) {
        this.name = name;
        this.monsterPool = monsters;
        this.lootPool = loot;
        this.levels = levels;
        this.monsterSpawnRate = monsterRate;
        this.lootSpawnRate = lootRate;
        this.entryFee = fee; 
    }

    public String getName() {return name;}
    public Monster getRandomMonster() {return monsterPool[(int) (Math.random() * monsterPool.length)];}
    public String getRandomLoot() {return lootPool[(int) (Math.random() * lootPool.length)];}
    public int getLevels() {return levels;}
    public double getMonsterSpawnRate() {return monsterSpawnRate;}
    public double getLootSpawnRate() {return lootSpawnRate;}
    public int getEntryFee() {return entryFee;}
}


