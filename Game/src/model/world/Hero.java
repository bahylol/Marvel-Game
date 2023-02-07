package model.world;

import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

import java.util.ArrayList;

public class Hero extends Champion{
    public Hero(String name,int maxHP,int mana,int maxActions,int speed,int attackRange,int attackDamage){
        super(name,maxHP,mana,maxActions,speed,attackRange,attackDamage);
    }

    public void useLeaderAbility(ArrayList<Champion> targets){
        for(int i=0;i<targets.size();i++) {
            for (int j = targets.get(i).getAppliedEffects().size() - 1; j >= 0; j--) {
                if (targets.get(i).getAppliedEffects().get(j).getType().equals(EffectType.DEBUFF)) {
                    targets.get(i).getAppliedEffects().get(j).remove(targets.get(i));
                    targets.get(i).getAppliedEffects().remove(j);
                }
            }
            Effect e= new Embrace(2);
            e.apply((Champion) targets.get(i));
            ((Champion) targets.get(i)).getAppliedEffects().add(e);
        }
    }

}
