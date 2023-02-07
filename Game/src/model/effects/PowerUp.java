package model.effects;

import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class PowerUp extends Effect{
    public PowerUp(int duration){
        super("PowerUp",duration,EffectType.BUFF);
    }

int x;

    public void apply(Champion c){
        for(int i=0;i<c.getAbilities().size();i++){
        if(c.getAbilities().get(i) instanceof DamagingAbility ){
            x= ((DamagingAbility) c.getAbilities().get(i)).getDamageAmount();
            ((DamagingAbility) c.getAbilities().get(i)).setDamageAmount((int)(x*1.2));}
        if(c.getAbilities().get(i) instanceof HealingAbility){
            x= ((HealingAbility) c.getAbilities().get(i)).getHealAmount();
            ((HealingAbility) c.getAbilities().get(i)).setHealAmount((int)(x*1.2));}
        }
    }

    public void remove(Champion c){
        for(int i=0;i<c.getAbilities().size();i++){
            if(c.getAbilities().get(i) instanceof DamagingAbility ){
                x= ((DamagingAbility) c.getAbilities().get(i)).getDamageAmount();
                ((DamagingAbility) c.getAbilities().get(i)).setDamageAmount((int)(x/1.2));}
            if(c.getAbilities().get(i) instanceof HealingAbility){
                x= ((HealingAbility) c.getAbilities().get(i)).getHealAmount();
                ((HealingAbility) c.getAbilities().get(i)).setHealAmount((int)(x/1.2));}
            }
        }


    }

