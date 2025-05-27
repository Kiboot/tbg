
package com.wow.tbg;

public class Monster extends Character {
    
    public Monster(String name, int health, int minATK, int maxATK, double armor, double speed) {
        super(name, health, minATK, maxATK, armor, speed);
        this.name = super.name;
    }
    //Sample Monster Spell/skill - for each spell create a new spell/skill method
    public void roar() {
        System.out.println(name + " lets out a terrifying roar!");
    }

}