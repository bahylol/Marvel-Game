package model.abilities;

import model.world.Damageable;

import java.util.ArrayList;

public abstract class Ability {
    private String name;
    private int manaCost;
    private int baseCooldown;
    private int currentCooldown;
    private int castRange;
    private int requiredActionPoints;
    private AreaOfEffect castArea;

    public Ability(String name,int cost,int baseCoolDown,int castRange,AreaOfEffect area,int required){
        this.name=name;
        manaCost=cost;
        this.baseCooldown=baseCoolDown;
        this.castRange=castRange;
        castArea=area;
        requiredActionPoints=required;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getBaseCooldown() {
        return baseCooldown;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
        if(currentCooldown>this.baseCooldown)
        	this.currentCooldown =this.baseCooldown;
        if(currentCooldown<0)
        	this.currentCooldown =0;
    }

    public int getCastRange() {
        return castRange;
    }

    public int getRequiredActionPoints() {
        return requiredActionPoints;
    }

    public AreaOfEffect getCastArea() {
        return castArea;
    }

    public abstract void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException;

}
