package model.world;
import java.awt.Point;
public class Cover implements Damageable{
    private int currentHP;//can never be below zero
    private Point location;

    public Cover(int x, int y){
        location=new Point(x,y);
        currentHP= (int )(100+(Math.random()*900));
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        if(currentHP<0)
            this.currentHP=0;
        else
            this.currentHP = currentHP;
    }

    public Point getLocation() {
        return location;
    }
}

