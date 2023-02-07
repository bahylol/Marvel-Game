package model.world;


import model.abilities.Ability;
import model.effects.Effect;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Champion implements Damageable,Comparable<Champion>{
    private String name;
    private int maxHP;
    private int currentHP;//can't be greater than the max hp and can't be zero
    private int mana;
    private int maxActionPointsPerTurn;
    private int currentActionPoints;
    private int attackRange;
    private int attackDamage;
    private int speed;
    private ArrayList<Ability> abilities;
    private ArrayList<Effect> appliedEffects;
    private Condition condition;//champion must start as ACTIVE
    private Point location;

    public Champion(String name,int maxHP,int mana,int maxActions,int speed,int attackRange,int attackDamage){
        this.name=name;
        this.maxHP=maxHP;
        currentHP=maxHP;
        this.mana=mana;
        maxActionPointsPerTurn=maxActions;
        currentActionPoints=maxActions;
        this.attackRange=attackRange;
        this.attackDamage=attackDamage;
        this.speed=speed;
        abilities=new ArrayList<Ability>();
        appliedEffects=new ArrayList<Effect>();
        condition=Condition.ACTIVE;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
    	this.currentHP = currentHP;
    	if(currentHP>maxHP)
    		this.currentHP=maxHP;
    	if(currentHP<0)
    		this.currentHP=0;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxActionPointsPerTurn() {
        return maxActionPointsPerTurn;
    }

    public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
        this.maxActionPointsPerTurn = maxActionPointsPerTurn;
    }

    public int getCurrentActionPoints() {
        return currentActionPoints;
    }

    public void setCurrentActionPoints(int currentActionPoints) {
        this.currentActionPoints = currentActionPoints;
        if(currentActionPoints>this.maxActionPointsPerTurn)
        	this.currentActionPoints =this.maxActionPointsPerTurn;
        if(currentActionPoints<0)
        	this.currentActionPoints =0;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public ArrayList<Effect> getAppliedEffects() {
        return appliedEffects;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public abstract void useLeaderAbility(ArrayList<Champion> targets);

    public int compareTo(Champion c){
        if(this.getSpeed()>c.getSpeed())
            return -1;
        if(this.getSpeed()<c.getSpeed())
            return 1;
       return this.getName().compareTo(c.getName());
    }

}
