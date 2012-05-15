package hive.basic;

public class AlienTank extends AlienBasic {
	private double moveSpeed = 65;
	private Game game;
	private int attackRange=85;
	private int attackSpeed=220;
	private int speedIncr=150;
    
	public AlienTank(Game game,Spawn spawn, String ref,int x,int y) {
		super(game,spawn, ref,x,y);
				
		this.game=game;
		life=20;
		dy=moveSpeed;
		worth=32;
		id=4;
	}
	public void update(long delta) {
		super.update(delta);
		if(y<game.ship.getY()){
			if(distanceTo(game.ship,7,7)<attackRange && y<game.ship.getY()){
				double dist=game.ship.getX()+7-x;
				double move=(delta * (attackSpeed*Integer.signum((int)(dist)))) / 1000;
				
				x+=Math.min(Math.abs(move),Math.abs(dist))*Integer.signum((int)dist) ;
				y+=(double)(delta*speedIncr/1000);
			}else{
				double dist=game.ship.getX()+7-x;
				double move=0;
				if(dist>0){
					dist-=50;
	
				}else if(dist<0){
					dist+=50;
				}
				
				move=(delta * (attackSpeed*Integer.signum((int)(dist)))) / 1000;
				x+=Math.min(Math.abs(move),Math.abs(dist))*Integer.signum((int)dist) ;
			}
		}
		//**remove checks to avoid limiting exploit
		if(x<=0){
			x=0;
		}
		if(x>=game.getWidth()-20){
			x=game.getWidth()-20;
		}
	}
}
