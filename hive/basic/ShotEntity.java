package hive.basic;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class ShotEntity extends Entity {
	private double moveSpeed = -300;
	private Game game;
	
	
	public ShotEntity(Game game,String sprite,int x,int y) {
		super(sprite,x,y);
		
		this.game = game;
		dy = moveSpeed;
		game.ammo--;
		game.stats.shotsFired++;
	}

	public void update(long delta) {
		super.update(delta);
		if (y < 0) {
			game.removeEntity(this);
		}
	}
	
	public void collidedWith(Entity other) {
		if (used) {
			return;
		}
	
		if (other instanceof AlienBasic && other.notUsed()) {
			game.removeEntity(this);
			used = true;
			AlienBasic a=(AlienBasic) other;
			a.life-=1;
			a.y-=20;
			if(a.getLife()==0){
				a.selfDestruct();
				game.score+=a.worth;
				game.addExplosion(x, y);
			}
		}
	}
}