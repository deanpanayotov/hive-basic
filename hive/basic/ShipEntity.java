package hive.basic;

import java.awt.Graphics;

public class ShipEntity extends Entity {

	private Game game;
	private int maxX;
	private int maxY;
	protected int life=3;
	private boolean visible;
	private long visibleMax=50;
	private long visibleNow;
	private boolean invulnerable;
	private long invulnerableMax=1000;
	private long invulnerableNow;
	public ShipEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);
		
		this.game = game;
		this.maxX = game.getWidth()-this.sprite.getWidth();
		this.maxY = game.getHeight()-this.sprite.getHeight();
		
		this.visible=true;
		this.visibleNow=0;
		this.invulnerable=false;
		this.invulnerableNow=0;
		
	}
	
	public void update(long delta) {
		
		super.update(delta);
		if (x < 0) {
			x=0;
		}
		if (x > maxX) {
			x=maxX;
		}
		if(y < 0) {
			y=0;
		}
		if(y > maxY) {
			y=maxY;
		}
		if(invulnerable){
			invulnerableNow-=delta;
			if(invulnerableNow<=0){
				invulnerable=false;
				visible=true;
			}else{
				visibleNow-=delta;
				if(visibleNow<=0){
					visible ^= true;
					visibleNow=50;
				}
			}
		}
		
	}
	
	public void draw(Graphics g) {
		if(visible){
			super.draw(g);
		}
	}
	public void addLife(){
		life+=1;
	}
	public int getLife(){
		return life;
	}
	
	public void collidedWith(Entity other) {
		// if its an alien, notify the game that the player
		// is dead
		if (other instanceof AlienBasic) {
			if(!invulnerable){
				life--;
				y+=20;
				invulnerable=true;
				invulnerableNow=invulnerableMax;
				game.hud.Message("You've lost a life!!!", 3);
				visibleNow=visibleMax;
				if(life==0){
					game.notifyDeath();
				}
			}
		}
	}
}