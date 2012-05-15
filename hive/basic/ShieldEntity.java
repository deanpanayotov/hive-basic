package hive.basic;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
public class ShieldEntity extends Entity{
	private Game game;
	public ShieldEntity(Game game,String sprite,int x,int y) {
		super(sprite,x,y);
		
		this.game = game;
	}
	public void update(long delta) {
		x=game.ship.getX()+16;
		y=game.ship.getY()+11;
		if(game.bonus!="shield"){
			game.removeEntity(this);
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.drawOval((int)x+16-60,(int)y-11-20, 80, 80);
	}
	
	public boolean collidesWith(Entity other,Graphics g) {	
		if(distanceTo(other, 0, 7 )<40){
			return true;
		}
		return false;
	}
	public void collidedWith(Entity other) {
		if (other instanceof AlienBasic && other.notUsed()) {
			AlienBasic a=(AlienBasic) other;
			a.life-=1;
			a.y-=20;
			a.x+=20*Integer.signum((int)(a.x-x));
			if(a.getLife()==0){
				a.selfDestruct();
				game.score+=a.worth;
				game.addExplosion(x, y);
			}
		}
	}
}
