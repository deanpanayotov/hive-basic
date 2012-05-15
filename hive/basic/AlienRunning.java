package hive.basic;

import java.util.Random;

public class AlienRunning extends AlienBasic {
	private Game game;

    private double slow_down; //vertical movement slow down
    private int slow_const=10; //constant for slowing down...
    private int dodge_const=100;
    
	
	public AlienRunning(Game game,Spawn spawn, String ref,int x,int y) {
		super(game,spawn, ref,x,y);
				
		this.game=game;
		life=3;
		worth=7;
		id=1;
	}
	public void update(long delta) {
		Random rand = new Random();	
		super.update(delta);		
        dx-=slow_down;
        if((dx>=0 && dx<slow_down) || (dx<=0 && dx>-slow_const)){
        	dx=rand.nextInt(3)-1;
        	slow_down=(rand.nextDouble()*slow_const)*dx;
        	dx*=dodge_const;
        	
        }
        if((x<=0 && dx<0) || (x>game.getWidth() && dx>0)){
    		dx*=-1;
    		slow_down*=-1;
    	}
	}

}