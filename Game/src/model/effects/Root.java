package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect{
    public Root(int duration){
        super("Root",duration,EffectType.DEBUFF);
    }

    public void apply(Champion c){
        if(!c.getCondition().equals(Condition.INACTIVE))
        c.setCondition(Condition.ROOTED);
    }

    public void remove(Champion c){
        if(c.getCondition().equals(Condition.INACTIVE))
        	c.setCondition(Condition.INACTIVE);
        else
            c.setCondition(Condition.ACTIVE);

        for(Effect effect :c.getAppliedEffects()){
            if(effect.getName().equals("Root"))
                c.setCondition(Condition.ROOTED);
        }
    }

}
