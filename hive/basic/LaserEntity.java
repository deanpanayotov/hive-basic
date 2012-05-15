package hive.basic;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class LaserEntity extends Entity {
	private double moveSpeed = -300;
	private Game game;

	private int	originY;
	private double beamLife;//in seconds;
	
	private Line2D line;
	private Rectangle2D rect;
	
	public LaserEntity(Game game,String sprite,int x,int y) {
		super(sprite,x,y);
		this.game = game;
		beamLife=0.05;
		originY=y;
		this.y=0;
		dy = moveSpeed;
		game.ammo-=5;
		game.stats.shotsFired++;
	}

	public void update(long delta) {
		super.update(delta);
		if (y < moveSpeed*beamLife) {
			game.removeEntity(this);
		}
	}
	
	public boolean collidesWith(Entity other,Graphics g) {	
		line=new Line2D.Double (this.x, this.y,this.x,originY);
		rect=new Rectangle2D.Double(other.getX(),(float) other.getY(),(float)other.sprite.getWidth(),(float)other.sprite.getHeight());
		return line.intersects(rect);
	}
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine((int)x, (int)y, (int)x, originY);
		g.drawString(this.x+" "+this.y+" "+this.px+" "+this.py, 30, 30);
	}
	public void collidedWith(Entity other) {
		if (other instanceof AlienBasic) {
			((AlienBasic) other).selfDestruct();
			game.score+=((AlienBasic) other).worth;
		}
	}
}
