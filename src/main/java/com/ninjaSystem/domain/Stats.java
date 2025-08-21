package com.ninjaSystem.domain;

public class Stats {
    private int attack;
    private int defense;
    private int chakra;
    public  Stats(int attack, int defense, int chakra){
        this.attack = attack;
        this.defense = defense;
        this.chakra = chakra;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }
    public int getChakra(){
        return chakra;
    }
    public void train(int atkInc, int defInc, int chakraInc){
        this.attack += atkInc;
        this.defense += defInc;
        this.chakra += chakraInc;
    }
}
