
package com.wow.tbg;



public class Hero extends Character {

    public Hero(String name, int health, int minATK, int maxATK, double armor, double speed) {
        super(name, health, minATK, maxATK, armor, speed);  // Passing double armor to Character class
    }

    //Hero sample skill/spell
    public void specialMove() {
        System.out.println(name + " uses a powerful strike!");
    }
    public void stun(){
    }
}

