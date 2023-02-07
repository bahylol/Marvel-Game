package engine;

import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;


import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private Object [] [] board;//5x5
    private static ArrayList<Champion> availableChampions;
    private static ArrayList<Ability> availableAbilities;
    private PriorityQueue turnOrder;
    private final static int BOARDHEIGHT=5;
    private final static int BOARDWIDTH=5;

    public Game(Player first,Player second){
        firstPlayer=first;
        secondPlayer=second;
        board =new Object[BOARDHEIGHT][BOARDWIDTH];
        availableChampions=new ArrayList<Champion>();
        availableAbilities=new ArrayList<Ability>();
        this.turnOrder =new PriorityQueue(6);//3 champions turns for each player
        placeCovers();
        placeChampions();
        prepareChampionTurns();
    }

    private void placeChampions(){//it places both player’s champions on the board
        for(int i=1;firstPlayer.getTeam().size()>=i;i++){
            board[0][i]=firstPlayer.getTeam().get(i-1);//place first player champions
            firstPlayer.getTeam().get(i-1).setLocation(new Point(0,i));//update first player champion location
        }
        for(int i=1;secondPlayer.getTeam().size()>=i;i++){
            board[4][i]=secondPlayer.getTeam().get(i-1);//place second player champions
            secondPlayer.getTeam().get(i-1).setLocation(new Point(4,i));//update second player champion location
        }
    }

    private void placeCovers(){//Places five covers on five random empty cells on the board
        //No cover should be placed on any of the four corners of the board
        int count=0;int x=0;int y=0;
        while(count!=5){
            x=(int)(1+(Math.random()*3));y=(int)(Math.random()*5);
            if(board[x][y]==null){
             board[x][y]=new Cover(x,y);
             count++;
            }
        }
    }


    public static void loadAbilities(String filePath) throws IOException {//method to load abilities
        String currentLine = "";
        FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader);
        while ((currentLine = br.readLine()) != null) {
            String []line=currentLine.split(",");//array containing abilities info
            if(line[0].equals("DMG")){
                availableAbilities.add(new DamagingAbility(line[1],
                        parseInt(line[2]),
                        parseInt(line[4]),
                        parseInt(line[3]),
                        AreaOfEffect.valueOf(line[5]),//convert from String to enum
                        parseInt(line[6]),
                        parseInt(line[7])));
            }
            if(line[0].equals("HEL")){
                availableAbilities.add(new HealingAbility(line[1],
                        parseInt(line[2]),
                        parseInt(line[4]),
                        parseInt(line[3]),
                        AreaOfEffect.valueOf(line[5]),
                        parseInt(line[6]),
                        parseInt(line[7])));
            }
            if(line[0].equals("CC")){
                availableAbilities.add(new CrowdControlAbility(line[1],
                        parseInt(line[2]),
                        parseInt(line[4]),
                        parseInt(line[3]),
                        AreaOfEffect.valueOf(line[5]),
                        parseInt(line[6]),
                        EffectCreator(line[7],line[8])));
            }
        }
    }

    public static Effect EffectCreator(//method to convert from String to effect
    	String x,String y){int z=parseInt(y);
        return switch (x) {
            case "Disarm" -> new Disarm(z);
            case "Dodge" -> new Dodge(z);
            case "Embrace" -> new Embrace(z);
            case "PowerUp" -> new PowerUp(z);
            case "Root" -> new Root(z);
            case "Shield" -> new Shield(z);
            case "Shock" -> new Shock(z);
            case "Silence" -> new Silence(z);
            case "SpeedUp" -> new SpeedUp(z);
            default -> new Stun(z);
        };
    }

    public static void loadChampions(String filePath) throws IOException {//method do load champions
        String currentLine = "";
        FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader);
        int i = 0;
        while ((currentLine = br.readLine()) != null) {
            String []line=currentLine.split(",");//array containing champions info
            if(line[0].equals("H")){
             availableChampions.add(new Hero(line[1],
                     parseInt(line[2]),
                     parseInt(line[3]),
                     parseInt(line[4]),
                     parseInt(line[5]),
                     parseInt(line[6]),
                     parseInt(line[7])));
            }
            if(line[0].equals("V")){
                availableChampions.add(new Villain(line[1],
                        parseInt(line[2]),
                        parseInt(line[3]),
                        parseInt(line[4]),
                        parseInt(line[5]),
                        parseInt(line[6]),
                        parseInt(line[7])));
            }
            if(line[0].equals("A")){
                availableChampions.add(new AntiHero(line[1],
                        parseInt(line[2]),
                        parseInt(line[3]),
                        parseInt(line[4]),
                        parseInt(line[5]),
                        parseInt(line[6]),
                        parseInt(line[7])));
            }
            //to add abilities for each champion
            availableChampions.get(i).getAbilities().add(locateAbility(line[8]));
            availableChampions.get(i).getAbilities().add(locateAbility(line[9]));
            availableChampions.get(i).getAbilities().add(locateAbility(line[10]));
            i++;
        }
    }

    private static Ability locateAbility(String string) {//method to convert string to ability
    	for (Ability ability : availableAbilities) {
			if(ability.getName().equals(string))
				return ability;
		}
    	return null;
	}

	public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public boolean isFirstLeaderAbilityUsed() {
        return firstLeaderAbilityUsed;
    }

    public boolean isSecondLeaderAbilityUsed() {
        return secondLeaderAbilityUsed;
    }

    public Object[][] getBoard() {
        return board;
    }

    public static ArrayList<Champion> getAvailableChampions() {
        return availableChampions;
    }

    public static ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public PriorityQueue getTurnOrder() {
        return turnOrder;
    }

    public static int getBoardheight() {
        return BOARDHEIGHT;
    }

    public static int getBoardwidth() {
        return BOARDWIDTH;
    }

    public Champion getCurrentChampion(){
        return (Champion) turnOrder.peekMin();}

    public Player checkGameOver(){
        if(firstPlayer.getTeam().isEmpty())
            return secondPlayer;
        if(secondPlayer.getTeam().isEmpty())
            return firstPlayer;
            return null;
    }

     public void move(Direction d) throws UnallowedMovementException,NotEnoughResourcesException {
        Champion c= getCurrentChampion();
        if(c.getCondition().equals(Condition.ROOTED))
            throw new UnallowedMovementException("Champion is Rooted");
        if (c.getCurrentActionPoints() - 1 < 0)
            throw new NotEnoughResourcesException("Not enough Action Points");
        else {
            int x = c.getLocation().x;
            int y = c.getLocation().y;
            switch (d) {
                case UP -> {
                    if (x + 1 >= BOARDHEIGHT)
                        throw new UnallowedMovementException("U Can't Move Out Of Board");
                    if(checkEmpty(x+1,y))
                        throw new UnallowedMovementException("Cell U Trying To Move To Is Full");

                    move_helper(c,x+1,y);
                }
                case DOWN -> {
                    if (x - 1 < 0)
                    	throw new UnallowedMovementException("U Can't Move Out Of Board");
                    if(checkEmpty(x-1,y))
                    	throw new UnallowedMovementException("Cell U Trying To Move To Is Full");

                    move_helper(c,x-1,y);
                }
                case RIGHT -> {
                    if (y +1 >= BOARDWIDTH)
                    	throw new UnallowedMovementException("U Can't Move Out Of Board");
                    if(checkEmpty(x,y+1))
                    	throw new UnallowedMovementException("Cell U Trying To Move To Is Full");

                    move_helper(c,x,y+1);
                }
                case LEFT -> {
                    if (y-1  < 0)
                    	throw new UnallowedMovementException("U Can't Move Out Of Board");
                    if(checkEmpty(x,y-1))
                    	throw new UnallowedMovementException("Cell U Trying To Move To Is Full");

                        move_helper(c,x,y-1);
                }
            }
            board[x][y]=null;
        }
    }

    public boolean checkEmpty(int x,int y){
        if(!(board[x][y] ==null))
            return true;
        return false;
    }

    public void move_helper(Champion c,int x,int y){
        c.setLocation(new Point(x , y));
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
        board[x][y]=c;
    }

   public void attack(Direction d) throws NotEnoughResourcesException,ChampionDisarmedException {
        Champion c= getCurrentChampion();
        if(c.getCurrentActionPoints()-2<0)
            throw new NotEnoughResourcesException("Not Enough Action Points");
        if(Disarm_check(c))
            throw new ChampionDisarmedException("Your Champion Is Disarmed");
       if(A_range(c,c.getAttackRange(),d) instanceof Cover){
           A_range(c,c.getAttackRange(),d).setCurrentHP(A_range(c,c.getAttackRange(),d).getCurrentHP()-c.getAttackDamage());
           c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
       }
        if(A_range(c,c.getAttackRange(),d) instanceof Champion) {
           if (!removeShield((Champion) A_range(c, c.getAttackRange(), d)))
           if(Dodge_check((Champion) A_range(c, c.getAttackRange(), d))){
            	double x=A_Multyplier(c,(Champion) A_range(c, c.getAttackRange(), d));
            	A_range(c,c.getAttackRange(),d).setCurrentHP((int) Math.round(A_range(c,c.getAttackRange(),d).getCurrentHP()-c.getAttackDamage()*x));}

                c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
        }
       KnokedoutRemove();
   }

    public boolean Disarm_check(Champion c){
        for(Effect effect :c.getAppliedEffects())
            if(effect.getName().equals("Disarm"))
                return true;
        return false;
    }
    public Damageable A_range(Champion c, int r, Direction d){
        int x =c.getLocation().x;
        int y=c.getLocation().y;
        Damageable ans=null;
        switch(d){
            case UP -> {
                for (int i = r; i > 0; i--)
                    if(x+i<5)
                        if(board[x+i][y] instanceof Champion &&NotSameTeam(c,(Champion) board[x+i][y] ))
                            ans =(Damageable) board[x+i][y];
                        else if(board[x+i][y] instanceof Cover)ans =(Damageable) board[x+i][y];
            }
            case DOWN -> {
                for (int i = r; i > 0; i--)
                    if(x-i>=0)
                        if(board[x-i][y] instanceof Champion &&NotSameTeam(c,(Champion) board[x-i][y] ))
                            ans =(Damageable) board[x-i][y];
                        else    if(board[x-i][y] instanceof Cover)ans =(Damageable) board[x-i][y];
            }
            case RIGHT -> {
                for (int i = r; i > 0; i--)
                    if(y+i<5)
                        if(board[x][y+i] instanceof Champion &&NotSameTeam(c,(Champion) board[x][y+i] ))
                            ans =(Damageable) board[x][y+i];
                        else if(board[x][y+i] instanceof Cover)ans =(Damageable) board[x][y+i];
            }
            case LEFT -> {
                for (int i = r; i > 0; i--)
                    if(y-i>=0)
                        if(board[x][y-i] instanceof Champion &&NotSameTeam(c,(Champion) board[x][y-i] ))
                            ans =(Damageable) board[x][y-i];
                        else if(board[x][y-i] instanceof Cover)ans =(Damageable) board[x][y-i];
            }
        }
                return ans;
    }

    public boolean Dodge_check(Champion c){
        double x= Math.random();
        for(Effect effect :c.getAppliedEffects())
            if(effect.getName().equals("Dodge"))
            	if(x<0.5)
            		return false;
        return true;
    }

    public double A_Multyplier(Champion c ,Champion d){
        if((c instanceof Hero && d instanceof Hero)||
        (c instanceof Villain && d instanceof Villain) ||
                (c instanceof AntiHero && d instanceof AntiHero))
            return 1;
        return 1.5;
    }
    public void CA_Champ(Champion c,Ability a){
        c.setMana(c.getMana()-a.getManaCost());
        c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
        for(int i=0;i<c.getAbilities().size();i++)
            if(c.getAbilities().get(i).equals(a))
                c.getAbilities().get(i).setCurrentCooldown(c.getAbilities().get(i).getBaseCooldown());
    }

    public void castAbility(Ability a) throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
        Champion c= getCurrentChampion();
        ArrayList<Damageable> targets= new ArrayList<>();
        if(CA_Action_Mana(c,a))
            throw new NotEnoughResourcesException("Not Enough Mana Or Action Points");
        if(CA_Silence(c))
            throw new AbilityUseException("Your Champion Is Silenced");
        if(a.getCurrentCooldown()!=0)
            throw new AbilityUseException("U Have To Wait For The Ability Cooldown");
        if(a.getCastArea().equals(AreaOfEffect.SELFTARGET)){
            targets.add(c);
            a.execute(targets);
        }
        if (a.getCastArea().equals(AreaOfEffect.SURROUND)) {
            if (!CA_surround(c, a).isEmpty()) {
                targets = CA_surround(c, a);
                for (int i = 0; i < targets.size(); i++) {
                    if(targets.get(i) instanceof Champion) {
                        if (a instanceof CrowdControlAbility)
                            if (((CrowdControlAbility) a).getEffect().getType().equals(EffectType.DEBUFF))
                                if (removeShield((Champion) targets.get(i)))
                                    targets.remove(i);
                        if (a instanceof DamagingAbility)
                            if (removeShield((Champion) targets.get(i)))
                                targets.remove(i);
                    }
                }
                a.execute(targets);
            }
        }
        if (a.getCastArea().equals(AreaOfEffect.TEAMTARGET)) {
            if (!CA_Teamtarget(c, a).isEmpty()) {
                targets = CA_Teamtarget(c, a);
                for (int i = targets.size()-1; i >=0 ; i--) {
                    if(a instanceof CrowdControlAbility)
                        if(((CrowdControlAbility)a).getEffect().getType().equals(EffectType.DEBUFF))
                            if (removeShield((Champion) targets.get(i) ))
                                targets.remove(i);
                    if(a instanceof DamagingAbility)
                        if (removeShield((Champion) targets.get(i) ))
                            targets.remove(i);
                }
                a.execute(targets);
            }
        }
        CA_Champ(c,a);
        KnokedoutRemove();
    }

    public void castAbility(Ability a, Direction d) throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
        Champion c= getCurrentChampion();
        if(CA_Action_Mana(c,a))
            throw new NotEnoughResourcesException("Not Enough Mana Or Action Points");
        if(CA_Silence(c))
            throw new AbilityUseException("Your Champion Is Silenced");
        if(a.getCurrentCooldown()!=0)
            throw new AbilityUseException("U Have To Wait For The Ability Cooldown");
        CA_Champ(c,a);
        ArrayList<Damageable> targets =  CA_range(c, c.getAttackRange(), d,a);
        if(!targets.isEmpty()) {
            for(int i=0;i<targets.size();i++) {
                if (targets.get(i) instanceof Champion){
                    if (a instanceof CrowdControlAbility)
                        if (((CrowdControlAbility) a).getEffect().getType().equals(EffectType.DEBUFF))
                            if (removeShield((Champion) targets.get(i)))
                                targets.remove(i);
                        if (a instanceof DamagingAbility)
                            if (removeShield((Champion) targets.get(i)))
                                targets.remove(i);
                    }
            }
        }
        a.execute(targets);
        KnokedoutRemove();
    }

    public void castAbility(Ability a, int x, int y) throws NotEnoughResourcesException, AbilityUseException, InvalidTargetException, CloneNotSupportedException {
        Champion c= getCurrentChampion();
        if(CA_Action_Mana(c,a))
            throw new NotEnoughResourcesException("Not Enough Mana Or Action Points");
        if(CA_Silence(c))
            throw new AbilityUseException("Your Champion Is Silenced");
        if(a.getCurrentCooldown()!=0)
            throw new AbilityUseException("U Have To Wait For The Ability Cooldown");
        if(CA_InvalidTargetException(c,(Damageable) board[x][y],a)) {
        	CA_Champ(c,a);
            throw new InvalidTargetException("Invalid target");}
        int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
        if(z>a.getCastRange()){
            CA_Champ(c,a);
            throw new AbilityUseException("Invalid target");}
        ArrayList<Damageable> targets= new ArrayList<>();
        targets.add((Damageable) board[x][y]);
        if(board[x][y] instanceof Champion)
            if(!removeShield((Champion)(board[x][y])))
                a.execute(targets);
        CA_Champ(c,a);
        KnokedoutRemove();
    }

    public boolean CA_Action_Mana(Champion c,Ability a){
        if(!(c.getMana()>=a.getManaCost()))
            return true;
        if(!(c.getCurrentActionPoints()>=a.getRequiredActionPoints()))
            return true;
        return false;
    }
    public boolean CA_Silence(Champion c){
        for(Effect effect :c.getAppliedEffects())
            if(effect.getName().equals("Silence"))
                return true;
        return false;
    }

    public boolean CA_InvalidTargetException(Champion c,Damageable d,Ability a){
        if(d instanceof Champion){
        if(!NotSameTeam(c,(Champion) d)){
            if(a instanceof HealingAbility)
                return false;
            if(a instanceof CrowdControlAbility)
                if(((CrowdControlAbility) a).getEffect().getType().equals(EffectType.BUFF))
                 return false;}
        if(NotSameTeam(c, (Champion) d)){
            if(a instanceof DamagingAbility)
                return false;
                  if  (a instanceof CrowdControlAbility)
                      if(((CrowdControlAbility) a).getEffect().getType().equals(EffectType.DEBUFF))
                            return false;}
    }
        if((d instanceof Cover)&& a instanceof DamagingAbility)
                return false;
        return true;
    }
    public ArrayList<Damageable> CA_range(Champion c, int r, Direction d,Ability a){
        int x =c.getLocation().x;
        int y=c.getLocation().y;
        ArrayList<Damageable> ans= new ArrayList<>();
        ArrayList<Damageable> Tans= new ArrayList<>();
        switch(d){
            case UP -> {
                for (int i = r; i > 0; i--)
                    if(x+i<5)
                            ans.add(CA_rangeH(x+i,y,c,a));
            }
            case DOWN -> {
                for (int i = r; i > 0; i--)
                    if(x-i>=0)
                        ans.add(CA_rangeH(x-i,y,c,a));
            }
            case RIGHT -> {
                for (int i = r; i > 0; i--)
                    if(y+i<5)
                        ans.add(CA_rangeH(x,y+i,c,a));
            }
            case LEFT -> {
                for (int i = r; i > 0; i--)
                    if(y-i>=0)
                        ans.add(CA_rangeH(x,y-i,c,a));
            }
        }
        for(int i=0;i<ans.size();i++)
            if(ans.get(i) != null)
                Tans.add(ans.get(i));
        return Tans;
    }

    public Damageable CA_rangeH(int x,int y,Champion c,Ability a){
        Damageable ans= null;
        if(a instanceof DamagingAbility){
            if(board[x][y] instanceof Champion)
            if(NotSameTeam(c,(Champion) board[x][y] ))
                ans=(Damageable) board[x][y];
            if(board[x][y] instanceof Cover)
                ans=(Damageable) board[x][y];}
        if(a instanceof HealingAbility){
            if(board[x][y] instanceof Champion)
            if(!(NotSameTeam(c,(Champion) board[x][y])))
                ans=(Damageable) board[x][y];
        }
        if(a instanceof CrowdControlAbility && board[x][y] instanceof Champion){
            if(((CrowdControlAbility)a).getEffect().getType().equals(EffectType.DEBUFF))
                if(NotSameTeam(c,(Champion) board[x][y] ))
                    ans=(Damageable) board[x][y];
            if(((CrowdControlAbility)a).getEffect().getType().equals(EffectType.BUFF))
                if(!(NotSameTeam(c,(Champion) board[x][y])))
                    ans=(Damageable) board[x][y];
        }

        return ans;
    }

    public ArrayList<Damageable> CA_surround(Champion c,Ability a){
        ArrayList<Point> targets= new ArrayList<>();
        ArrayList<Damageable> ans= new ArrayList<>();
        int x =c.getLocation().x;
        int y=c.getLocation().y;
        for(int i=-1;i<2;i++){
            if(x+i<BOARDHEIGHT && x+i>=0 && y-1>=0)
            if(board[x+i][y-1] instanceof Damageable)
                targets.add(new Point(x+i,y-1));
            if(x+i<BOARDHEIGHT && x+i>=0 && y+1<BOARDWIDTH)
            if(board[x+i][y+1] instanceof Damageable)
                targets.add(new Point(x+i,y+1));}
        if(x+1<BOARDHEIGHT)
        if(board[x+1][y] instanceof Damageable)
            targets.add(new Point(x+1,y));
        if(x-1>=0 )
        if(board[x-1][y] instanceof Damageable )
            targets.add(new Point(x-1,y));
        for(int i=0;i<targets.size();i++){
            int z=targets.get(i).getLocation().x;
            int w=targets.get(i).getLocation().y;
            if(a instanceof DamagingAbility) {
            	if(board[z][w] instanceof Champion)
            		if(NotSameTeam(c,(Champion) board[z][w]))
                ans.add((Damageable) board[z][w]);
                if(board[z][w] instanceof Cover)
                    ans.add((Damageable) board[z][w]);
                }
            if(board[z][w] instanceof Champion) {
                if(a instanceof HealingAbility)
                    if(!NotSameTeam(c,(Champion) board[z][w]))
                        ans.add((Damageable) board[z][w]);
                if(a instanceof CrowdControlAbility){
                    if((((CrowdControlAbility)a).getEffect().getType().equals(EffectType.DEBUFF)) &&
                    NotSameTeam(c,(Champion) board[z][w]))
				        ans.add((Damageable) board[z][w]);
                if(((CrowdControlAbility)a).getEffect().getType().equals(EffectType.BUFF) &&
                    !NotSameTeam(c,(Champion) board[z][w]))
                        ans.add((Damageable) board[z][w]);
                }
            }
        }
        return ans;
    }
    public ArrayList<Damageable> CA_Teamtarget(Champion c,Ability a){
        ArrayList<Damageable> ans= new ArrayList<>();
        if(a instanceof CrowdControlAbility) {
        	if(((CrowdControlAbility) a).getEffect().getType().equals(EffectType.DEBUFF)) {
        		if(firstPlayer.getTeam().contains(c))
                	for(int i=0;i<secondPlayer.getTeam().size();i++) {
                		int x=secondPlayer.getTeam().get(i).getLocation().x;
                		int y=secondPlayer.getTeam().get(i).getLocation().y;
                		int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
                		if(z<=a.getCastRange())
                			ans.add(secondPlayer.getTeam().get(i));
                			}
        		if(secondPlayer.getTeam().contains(c))
        			for(int i=0;i<firstPlayer.getTeam().size();i++) {
        				int x=firstPlayer.getTeam().get(i).getLocation().x;
        				int y=firstPlayer.getTeam().get(i).getLocation().y;
        				int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
        				if(z<=a.getCastRange())
        					ans.add(firstPlayer.getTeam().get(i));
            				}
            	}
        }
        if(a instanceof DamagingAbility) {
        	if(firstPlayer.getTeam().contains(c))
            	for(int i=0;i<secondPlayer.getTeam().size();i++) {
            		int x=secondPlayer.getTeam().get(i).getLocation().x;
            		int y=secondPlayer.getTeam().get(i).getLocation().y;
            		int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
            		if(z<=a.getCastRange())
            			ans.add(secondPlayer.getTeam().get(i));
            			}
    	if(secondPlayer.getTeam().contains(c))
        	for(int i=0;i<firstPlayer.getTeam().size();i++) {
        		int x=firstPlayer.getTeam().get(i).getLocation().x;
        		int y=firstPlayer.getTeam().get(i).getLocation().y;
        		int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
        		if(z<=a.getCastRange())
        			ans.add(firstPlayer.getTeam().get(i));
        			}
        	}
        	if(a instanceof HealingAbility) {
        		if(firstPlayer.getTeam().contains(c))
                	for(int i=0;i<firstPlayer.getTeam().size();i++) {
                		int x=firstPlayer.getTeam().get(i).getLocation().x;
                		int y=firstPlayer.getTeam().get(i).getLocation().y;
                		int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
                		if(z<=a.getCastRange())
                			ans.add(firstPlayer.getTeam().get(i));
                			}
        		if(secondPlayer.getTeam().contains(c))
                	for(int i=0;i<secondPlayer.getTeam().size();i++) {
                		int x=secondPlayer.getTeam().get(i).getLocation().x;
                		int y=secondPlayer.getTeam().get(i).getLocation().y;
                		int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
                		if(z<=a.getCastRange())
                			ans.add(secondPlayer.getTeam().get(i));
                			}
        		}
        	if(a instanceof CrowdControlAbility) {
        		if(((CrowdControlAbility) a).getEffect().getType().equals(EffectType.BUFF)){
        			if(firstPlayer.getTeam().contains(c))
        				for(int i=0;i<firstPlayer.getTeam().size();i++) {
                			int x=firstPlayer.getTeam().get(i).getLocation().x;
                			int y=firstPlayer.getTeam().get(i).getLocation().y;
                			int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
                			if(z<=a.getCastRange())
                				ans.add(firstPlayer.getTeam().get(i));
                			}
        			if(secondPlayer.getTeam().contains(c))
        				for(int i=0;i<secondPlayer.getTeam().size();i++) {
        					int x=secondPlayer.getTeam().get(i).getLocation().x;
        					int y=secondPlayer.getTeam().get(i).getLocation().y;
        					int z=Math.abs(c.getLocation().x-x)+Math.abs(c.getLocation().y-y);
        					if(z<=a.getCastRange())
        						ans.add(secondPlayer.getTeam().get(i));
                			}
        			}
        		}
        return ans;
    }

    public boolean NotSameTeam(Champion c,Champion d){
        for(int i=0;i<firstPlayer.getTeam().size();i++)
            for(int j=0;j<secondPlayer.getTeam().size();j++)
            if (c == firstPlayer.getTeam().get(i)&& d==secondPlayer.getTeam().get(j))
                return true;
        for(int i=0;i<secondPlayer.getTeam().size();i++)
            for(int j=0;j<firstPlayer.getTeam().size();j++)
                if (d == firstPlayer.getTeam().get(j)&& c==secondPlayer.getTeam().get(i))
                    return true;
        return false;
    }

    public boolean removeShield(Champion c){
        for(Effect effect :c.getAppliedEffects())
            if (effect instanceof Shield){
                effect.remove(c);
                c.getAppliedEffects().remove(effect);
            return true;}

        return false;
    }

   public void useLeaderAbility() throws LeaderNotCurrentException,LeaderAbilityAlreadyUsedException {
       Champion c=getCurrentChampion();
       if(!(c==firstPlayer.getLeader()||c==secondPlayer.getLeader()))
           throw new LeaderNotCurrentException("Champion Is Not A Leader");

       for(int i=0;i<firstPlayer.getTeam().size();i++)
           if(c==firstPlayer.getTeam().get(i))
               if (firstLeaderAbilityUsed)
                   throw new LeaderAbilityAlreadyUsedException("Your Leader Ability Is Already Used");
               else {if(c instanceof Hero)
                   c.useLeaderAbility(firstPlayer.getTeam());
                   if (c instanceof Villain)
                       c.useLeaderAbility(secondPlayer.getTeam());
                   if(c instanceof AntiHero)
                       c.useLeaderAbility(ULAhelper());
                   firstLeaderAbilityUsed=true;
               }


       for(int i=0;i<secondPlayer.getTeam().size();i++)
           if(c==secondPlayer.getTeam().get(i))
             if(secondLeaderAbilityUsed)
            	 throw new LeaderAbilityAlreadyUsedException("Your Leader Ability Is Already Used");
           else {if(c instanceof Hero)
                c.useLeaderAbility(secondPlayer.getTeam());
               if (c instanceof Villain)
                   c.useLeaderAbility(firstPlayer.getTeam());
               if(c instanceof AntiHero)
                   c.useLeaderAbility(ULAhelper());
               secondLeaderAbilityUsed=true;
           }
       KnokedoutRemove();
   }
   public ArrayList<Champion> ULAhelper(){
       ArrayList<Champion> x=new ArrayList<>();
       for(int i=0;i<3;i++) {
           if (firstPlayer.getTeam().get(i) !=firstPlayer.getLeader())
               x.add(firstPlayer.getTeam().get(i));
           if (secondPlayer.getTeam().get(i) !=secondPlayer.getLeader())
               x.add(secondPlayer.getTeam().get(i));
       }
       return x;
   }

   public void KnokedoutRemove(){
       for(int i=0;i<BOARDHEIGHT;i++)
           for(int j=0;j<BOARDWIDTH;j++) {
               if (board[i][j] instanceof Damageable)
                   if (((Damageable) board[i][j]).getCurrentHP() == 0) {
                       if (board[i][j] instanceof Champion) {
                           ((Champion) board[i][j]).setCondition(Condition.KNOCKEDOUT);
                           removeK((Champion) board[i][j]);
                           board[i][j] = null;
                       }
                       if (board[i][j] instanceof Cover)
                           board[i][j] = null;
                   }
           }
   }

   public void removeK(Champion c){
        for(int i=0;i<firstPlayer.getTeam().size();i++)
            if(c.equals(firstPlayer.getTeam().get(i))) {
                firstPlayer.getTeam().remove(i);

        			ArrayList<Champion> temp = new ArrayList<>();
        			while (!turnOrder.isEmpty()) {
        				temp.add((Champion) turnOrder.remove());
        			}
        			temp.remove(c);
        			while (!temp.isEmpty()) {
        				turnOrder.insert(temp.remove(0));
        			}
            }
       for(int i=0;i<secondPlayer.getTeam().size();i++)
           if(c.equals(secondPlayer.getTeam().get(i))) {
               secondPlayer.getTeam().remove(i);
               ArrayList<Champion> temp = new ArrayList<>();
   			while (!turnOrder.isEmpty()) {
   				temp.add((Champion) turnOrder.remove());
   			}
   			temp.remove(c);
   			while (!temp.isEmpty()) {
   				turnOrder.insert(temp.remove(0));
   			}
   		}
   }

   public void endTurn() {
		turnOrder.remove();
		if (turnOrder.isEmpty())
			prepareChampionTurns();
		updateChampionTimersAndActions();
		if (getCurrentChampion().getCondition() == Condition.INACTIVE)
			endTurn();
	}

	private void updateChampionTimersAndActions() {
		Champion c = getCurrentChampion();
		for (Ability ability : c.getAbilities()) {
			if (ability.getCurrentCooldown() > 0)
				ability.setCurrentCooldown(ability.getCurrentCooldown() - 1);
		}
		for (int i = 0; i < c.getAppliedEffects().size(); i++) {
			Effect e = c.getAppliedEffects().get(i);
			e.setDuration(e.getDuration() - 1);
			if (e.getDuration() == 0) {
				e.remove(c);
				c.getAppliedEffects().remove(i);
				i--;
			}
		}
		c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
	}

    private void prepareChampionTurns(){
            for(int i=0;i<firstPlayer.getTeam().size();i++)
                if(!(firstPlayer.getTeam().get(i).getCondition().equals(Condition.KNOCKEDOUT)))
                    turnOrder.insert(firstPlayer.getTeam().get(i));
        for(int i=0;i<secondPlayer.getTeam().size();i++)
                if(!(secondPlayer.getTeam().get(i).getCondition().equals(Condition.KNOCKEDOUT)))
                    turnOrder.insert(secondPlayer.getTeam().get(i));
             checkGameOver();
    }

    public static String Championdet(String name) {
    	String x="";
        for (Champion availableChampion : availableChampions)
            if (availableChampion.getName().equals(name)) {
                x += "Name : " + availableChampion.getName() + "\n";
                x += "Max HP : " + availableChampion.getMaxHP() + "\n";
                x += "Mana : " + availableChampion.getMana() + "\n";
                x += "Max ActionPoints per Turn : " + availableChampion.getMaxActionPointsPerTurn() + "\n";
                x += "Attack Range : " + availableChampion.getAttackRange() + "\n";
                x += "Attack Damage : " + availableChampion.getAttackDamage() + "\n";
                x += "Speed : " + availableChampion.getSpeed() + "\n";
                if(availableChampion instanceof Hero)
                	x+="Champion type : Hero";
                if(availableChampion instanceof Villain)
                	x+="Champion type : Villain";
                if(availableChampion instanceof AntiHero)
                	x+="Champion type : AntiHero";
            }
    	return x;
    }

    public String Championdet1(String name){
    	String x="";
        for (int i=0;i<firstPlayer.getTeam().size();i++)
            if (firstPlayer.getTeam().get(i).getName().equals(name)) {
                x += "Name : " + firstPlayer.getTeam().get(i).getName() + "\n";
                x += "Current HP : " + firstPlayer.getTeam().get(i).getCurrentHP() + "\n";
                x += "Mana : " + firstPlayer.getTeam().get(i).getMana() + "\n";
                x += "Current ActionPoints : " + firstPlayer.getTeam().get(i).getCurrentActionPoints() + "\n";
                x += "Attack Range : " + firstPlayer.getTeam().get(i).getAttackRange() + "\n";
                x += "Attack Damage : " + firstPlayer.getTeam().get(i).getAttackDamage() + "\n";
                x += "Speed : " + firstPlayer.getTeam().get(i).getSpeed() + "\n";
                if(firstPlayer.getTeam().get(i) instanceof Hero)
                	x+="Champion type : Hero";
                if(firstPlayer.getTeam().get(i) instanceof Villain)
                	x+="Champion type : Villain";
                if(firstPlayer.getTeam().get(i) instanceof AntiHero)
                	x+="Champion type : AntiHero";
            }
        for (int i=0;i<secondPlayer.getTeam().size();i++)
            if (secondPlayer.getTeam().get(i).getName().equals(name)) {
                x += "Name : " + secondPlayer.getTeam().get(i).getName() + "\n";
                x += "Current HP : " + secondPlayer.getTeam().get(i).getCurrentHP() + "\n";
                x += "Mana : " + secondPlayer.getTeam().get(i).getMana() + "\n";
                x += "Current ActionPoints : " + secondPlayer.getTeam().get(i).getCurrentActionPoints() + "\n";
                x += "Attack Range : " + secondPlayer.getTeam().get(i).getAttackRange() + "\n";
                x += "Attack Damage : " + secondPlayer.getTeam().get(i).getAttackDamage() + "\n";
                x += "Speed : " + secondPlayer.getTeam().get(i).getSpeed() + "\n";
                if(secondPlayer.getTeam().get(i) instanceof Hero)
                	x+="Champion type : Hero";
                if(secondPlayer.getTeam().get(i) instanceof Villain)
                	x+="Champion type : Villain";
                if(secondPlayer.getTeam().get(i) instanceof AntiHero)
                	x+="Champion type : AntiHero";
            }
        
    	return x;

    }
    
    public String ChampAEffects(String name) {
    	String x="";
    	 for (int i=0;i<firstPlayer.getTeam().size();i++)
             if (firstPlayer.getTeam().get(i).getName().equals(name)) {
            	 for(int j=0;j<firstPlayer.getTeam().get(i).getAppliedEffects().size();j++){
            		 x+="Effect Name : "+ firstPlayer.getTeam().get(i).getAppliedEffects().get(j).getName()+"\n";
            		 x+="Effect Type : "+firstPlayer.getTeam().get(i).getAppliedEffects().get(j).getType()+"\n";
            		 x+="Effect Duration : "+firstPlayer.getTeam().get(i).getAppliedEffects().get(j).getDuration()+"\n"+"\n";
            	 }
             }
    	 for (int i=0;i<secondPlayer.getTeam().size();i++)
             if (secondPlayer.getTeam().get(i).getName().equals(name)) {
            	 for(int j=0;j<secondPlayer.getTeam().get(i).getAppliedEffects().size();j++){
            		 x+="Effect Name : "+ secondPlayer.getTeam().get(i).getAppliedEffects().get(j).getName()+"\n";
            		 x+="Effect Type : "+secondPlayer.getTeam().get(i).getAppliedEffects().get(j).getType()+"\n";
            		 x+="Effect Duration : "+secondPlayer.getTeam().get(i).getAppliedEffects().get(j).getDuration()+"\n"+"\n";
            	 }
             }
    	 if(x.equals(""))
    		 x="No applied Effects";
    	return x;
    }
    
    public String AbilityChamp(String name) {
    	String x= "";
    	Champion c=getCurrentChampion();
    	for(int i=0;i<c.getAbilities().size();i++)
    		if(c.getAbilities().get(i).getName().equals(name)) {
    			x+="Name : "+c.getAbilities().get(i).getName()+"\n";
                x+="Mana Cost : "+c.getAbilities().get(i).getManaCost()+"\n";
                x+="Base CoolDown : "+c.getAbilities().get(i).getBaseCooldown()+"\n";
                x+="Current CoolDown : "+c.getAbilities().get(i).getCurrentCooldown()+"\n";
                x+="Cast Range : "+c.getAbilities().get(i).getCastRange()+"\n";
                x+="Required Action Points : "+c.getAbilities().get(i).getRequiredActionPoints()+"\n";
                x+="Cast Area : "+c.getAbilities().get(i).getCastArea().toString()+"\n";
                if(c.getAbilities().get(i) instanceof DamagingAbility){
                    x+="Ability Type : DamagingAbility"+"\n";
                    x+="Damaging Amount : "+((DamagingAbility)c.getAbilities().get(i)).getDamageAmount()+"\n";}
                if(c.getAbilities().get(i) instanceof HealingAbility){
                    x+="Ability Type : HealingAbility"+"\n";
                    x+="Healing Amount : "+((HealingAbility)c.getAbilities().get(i)).getHealAmount()+"\n";}
                if(c.getAbilities().get(i) instanceof CrowdControlAbility){
                    x+="Ability Type : CrowdControlAbility"+"\n";
                    x+="Effect : "+((CrowdControlAbility)c.getAbilities().get(i)).getEffect().getName()+"\n";
                    x+="Effect Type : "+((CrowdControlAbility)c.getAbilities().get(i)).getEffect().getType()+"\n";
                    x+="Effect Duration : "+((CrowdControlAbility)c.getAbilities().get(i)).getEffect().getDuration()+"\n";}
    		}
    			
    	return x;
    }

    public static String Leaderdet(String name) {
    	 for (Champion availableChampion : availableChampions)
             if (availableChampion.getName().equals(name)) {
                 if(availableChampion instanceof Hero)
                 	return name +"\n"+"Hero: Removes all negative effects from the players entire team and adds an Embrace effect"
                 			+ "to them which lasts for 2 turns.";
                 if(availableChampion instanceof Villain)
                	 return name +"\n"+"Villain: Immediately eliminates (knocks out) all enemy champions with less than 30% health"
                	 		+ "points.";
                 if(availableChampion instanceof AntiHero)
                	 return name +"\n"+"AntiHero: All champions on the board except for the leaders of each team will be stunned"
                	 		+ "for 2 turns.";
             }
    	 return "" ;
    }

    public static String abilitiedet(String name) {
        String x="";
        for (Ability availableAbility : availableAbilities)
            if (availableAbility.getName().equals(name)) {
                x+="Name : "+availableAbility.getName()+"\n";
                x+="Mana Cost : "+availableAbility.getManaCost()+"\n";
                x+="Base CoolDown : "+availableAbility.getBaseCooldown()+"\n";
                x+="Cast Range : "+availableAbility.getCastRange()+"\n";
                x+="Required Action Points : "+availableAbility.getRequiredActionPoints()+"\n";
                x+="Cast Area : "+availableAbility.getCastArea().toString()+"\n";
                if(availableAbility instanceof DamagingAbility){
                    x+="Ability Type : DamagingAbility"+"\n";
                    x+="Damaging Amount : "+((DamagingAbility)availableAbility).getDamageAmount()+"\n";}
                if(availableAbility instanceof HealingAbility){
                    x+="Ability Type : HealingAbility"+"\n";
                    x+="Healing Amount : "+((HealingAbility)availableAbility).getHealAmount()+"\n";}
                if(availableAbility instanceof CrowdControlAbility){
                    x+="Ability Type : CrowdControlAbility"+"\n";
                    x+="Effect : "+((CrowdControlAbility)availableAbility).getEffect().getName()+"\n";
                    x+="Effect Type : "+((CrowdControlAbility)availableAbility).getEffect().getType()+"\n";
                    x+="Effect Duration : "+((CrowdControlAbility)availableAbility).getEffect().getDuration()+"\n";}
            }
            return x;
    }

}
