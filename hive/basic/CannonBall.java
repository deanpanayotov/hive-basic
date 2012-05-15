package hive.basic;

public class CannonBall extends ShotEntity{
	private double moveSpeed = -250;
	private Game game;
	
	public CannonBall(Game game,String sprite,int x,int y){
		super(game,sprite,x,y);
		this.game = game;
		dy = moveSpeed;
		game.ammo-=3;
		game.stats.shotsFired++;
	}
	public void collidedWith(Entity other) {
		if (other instanceof AlienBasic && other.notUsed()) {
			AlienBasic a=(AlienBasic) other;
			a.selfDestruct();
			game.score+=a.worth;
			game.addExplosion(x, y);
		}
	}
}
