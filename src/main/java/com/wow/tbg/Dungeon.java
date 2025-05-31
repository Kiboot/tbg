

public class Dungeon {
    private String name;
    private Monster[] monsterPool;
    private String[] lootPool;
    private int levels;
    private double monsterSpawnRate;
    private double lootSpawnRate;
    private int entryFee; // 💰 New field for entry cost

    public Dungeon(String name, int levels, double monsterRate, double lootRate, int fee, Monster[] monsters, String[] loot) {
        this.name = name;
        this.monsterPool = monsters;
        this.lootPool = loot;
        this.levels = levels;
        this.monsterSpawnRate = monsterRate;
        this.lootSpawnRate = lootRate;
        this.entryFee = fee; 
    }

    public String getName() {return name;}

    public Monster getRandomMonster() {
    Monster original = monsterPool[(int) (Math.random() * monsterPool.length)];
    return new Monster(original.getName(), original.getHealth(), original.getMinATK(),
        original.getMaxATK(), original.getArmor(), original.getSpeed());
}

    public String getRandomLoot() {return lootPool[(int) (Math.random() * lootPool.length)];}
    public int getLevels() {return levels;}
    public double getMonsterSpawnRate() {return monsterSpawnRate;}
    public double getLootSpawnRate() {return lootSpawnRate;}
    public int getEntryFee() {return entryFee;}
}


