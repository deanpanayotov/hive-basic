package hive.basic;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.*;


public abstract class Entity {
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected double px;
	protected double py;
	protected boolean used=false;
	
	protected Sprite sprite;
	
	protected Rectangle me = new Rectangle();
	protected Rectangle him = new Rectangle();
	
	public Entity(String ref,int x,int y) {
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}
	
	public void update(long delta) {
		px=x;
		py=y;
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}
	
	public void setHorizontalMovement(double dx) {
		this.dx = dx;
	}
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}
	public double getHorizontalMovement() {
		return dx;
	}
	public double getVerticalMovement() {
		return dy;
	}

	/*public void setX(double ax,boolean relative){
		if(relative){
			this.x+=ax;
		}else{
			this.x=ax;
		}
	}
	public void setY(double ay,boolean relative){
		if(relative){
			this.y+=ay;
		}else{
			this.y=ay;
		}
	}
	public void setX(double ax){
		this.x=ax;
	}*/
	public void draw(Graphics g) {
		sprite.draw(g,(int) x,(int) y);
	}

	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}
	public boolean notUsed(){
		return !used;
	}
	public double distanceTo(Entity other,double offX,double offY){
		double a=Math.abs(x-other.getX()+offX);
		double b=Math.abs(y-other.getY()+offY);
		return (double)(Math.sqrt(a*a+b*b));
	}
	
	public boolean collidesWith(Entity other, Graphics g) {
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}
	
	/**
	 * Notification that this entity collided with another.
	 * 
	 * @param other The entity with which this entity collided.
	 */
	public abstract void collidedWith(Entity other);
}