import java.util.Scanner;

class Faction {
    String name;
    String description;
    int strengthBonus;
    int magicBonus;
    int agilityBonus;

    public Faction(String name, String description, int strengthBonus, int magicBonus, int agilityBonus) {
        this.name = name;
        this.description = description;
        this.strengthBonus = strengthBonus;
        this.magicBonus = magicBonus;
        this.agilityBonus = agilityBonus;
    }

    public void displayInfo() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Faction: " + name);
        System.out.println(description);
        System.out.println("Strength: +" + strengthBonus + " | Magic: +" + magicBonus + " | Agility: +" + agilityBonus);
        System.out.println("=".repeat(40));
    }
        public String getFormattedStats() {
        return "<html><center><strong>" + name + "</strong><br>" + description + "<br>"
                + "Strength: +" + strengthBonus + " | Magic: +" + magicBonus + " | Agility: +" + agilityBonus
                + "</center></html>";
    }
}