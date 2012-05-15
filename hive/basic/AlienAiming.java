package hive.basic;


public class AlienAiming extends AlienBasic {
	private Game game;
	private int attackRange=150;
	private int attackSpeed=150;
    
	public AlienAiming(Game game,Spawn spawn, String ref,int x,int y) {
		super(game,spawn, ref,x,y);
				
		this.game=game;
		life=5;
		worth=13;
		id=2;
		attackSpeed*=game.relativeSpeedConst;
	}
	public void update(long delta) {
		super.update(delta);
		if(distanceTo(game.ship,13,13)<attackRange && y<game.ship.getY()){
			x+=(delta * (attackSpeed*(1+game.speedIncrease)*Integer.signum((int)(game.ship.getX()+13-x)))) / 1000 ;
		}
		if(x<=0){
			x=0;
		}
		if(x>=game.getWidth()-20){
			x=game.getWidth()-20;
		}
	}
}
