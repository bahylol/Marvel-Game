package model.abilities;
import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

import java.util.ArrayList;

public class CrowdControlAbility extends Ability{
    private Effect effect;

    public CrowdControlAbility(String name,int cost,int baseCoolDown,int castRange,AreaOfEffect area,int required,Effect effect){
        super(name,cost,baseCoolDown,castRange,area,required);
        this.effect=effect;
    }

    public Effect getEffect() {
        return effect;
    }

    public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
        for(int i=0;i<targets.size();i++) {
            Effect e= (Effect)effect.clone();
            e.apply((Champion) targets.get(i));
            ((Champion) targets.get(i)).getAppliedEffects().add(e);
        }
    }

}
