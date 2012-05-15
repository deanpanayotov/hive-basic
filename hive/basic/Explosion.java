package hive.basic;

public class Explosion extends Entity {
	private Game game;
	private int life;
	public Explosion(Game game, String sprite,int x,int y){
		super(sprite,x,y);
		this.game=game;
		life=120;
	}
	public void update(long delta) {
		life-=delta;
		if(life<=0){
			game.removeEntity(this);
		}
	}
	
	public void collidedWith(Entity other) {
		if ( other instanceof AlienBasic && other.notUsed()) {
			((AlienBasic)other).selfDestruct();
			game.addExplosion(x, y);
		}
	}
	
}
