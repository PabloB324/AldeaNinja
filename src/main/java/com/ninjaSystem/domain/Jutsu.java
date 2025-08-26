package com.ninjaSystem.domain;

public class Jutsu {
    private final String name;
    private final int chakraCost;

    public Jutsu(String name , int chakraCost){
        this.name = name;
        this.chakraCost = chakraCost;
    }
    
    public String getName(){
        return name;
    }
    public int getChakraCost(){
        return chakraCost;
    }
}
