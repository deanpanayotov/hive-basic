package hive.basic;

import java.awt.Graphics;
import java.awt.Color;

public class Statistics {
	private Game game;
	private Sprite sprite;
	
	protected int aliensKilled[]={ 0,0,0,0,0};
	private String alienSpr[]={ "Blu","Red","Pur","Gre","Yel"};
	protected int shotsFired=0;
	protected long timeBegan=0;
	protected long timeEnded=0;
	protected int minutes=0;
	protected int seconds=0;
	protected String Out1;
	protected String Out2;
	
	public Statistics(Game game){
		this.game=game;
	}
	public void formOutput(){
		Out1="AliensKilled:";
		minutes=(int)((timeEnded-timeBegan)/1000)/60;
		seconds=(int)((timeEnded-timeBegan)/1000)%60;
		Out2="Shots fired: "+shotsFired+" Time Elapsed: "+minutes+":"+seconds+" Score: "+game.score;
	}
	public void display(Graphics g){
		g.setColor(Color.white);
		g.drawString(Out1,
				(800 - g.getFontMetrics().stringWidth(Out1)) / 2,
				250);
		for(int i=0;i<5;i++){
			sprite = SpriteStore.get().getSprite("sprites/anAlien"+alienSpr[i]+"2.gif");
			sprite.draw(g,(int) 372,(int) 270+20*i);
			g.drawString("    :"+aliensKilled[i], 390, 280+20*i);
		}
		g.drawString(Out2,
				(800 - g.getFontMetrics().stringWidth(Out2)) / 2,
				500);
		g.drawRect(350, 230, 100, 150);
	}
	public void clear(){
		aliensKilled[0]=0;
		aliensKilled[1]=0;
		aliensKilled[2]=0;
		aliensKilled[3]=0;
		aliensKilled[4]=0;
		
		shotsFired=0;
		timeBegan=0;
		minutes=0;
		seconds=0;
	}
}
