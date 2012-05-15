package hive.basic;

import java.util.Random;


public class Bonus extends Entity{
	private Game game;
	private Spawn spawn;
	private String spr;
	private int type;
	private double moveSpeed = 40;
	
	public Bonus(Game game,Spawn spawn, String sprite,int x,int y){
		super(sprite,x,y);
		Random rand=new Random();
		this.type=rand.nextInt(7);
		switch(this.type){
			case 0:
			default: {spr="sprites/barrel.gif"; break;}
			case 1: {spr="sprites/medkit.gif"; break;}
			case 2: {spr="sprites/bullet.gif"; break;}
			case 3: {spr="sprites/double.gif"; break;}
			case 4: {spr="sprites/triple.gif"; break;}
			case 5: {spr="sprites/cannon.gif"; break;}
			case 6: {spr="sprites/shield.gif"; break;}
			case 7: {spr="sprites/laser.gif"; break;}
		}
		super.sprite=SpriteStore.get().getSprite(spr);
		this.game=game;
		this.spawn=spawn;
		dy = moveSpeed;
	}
	
	public void update(long delta){
		super.update(delta);
		if (y > game.getHeight()){
			game.removeEntity(this);
		}	
	}
	public void collidedWith(Entity other) {
		if(!used){
			if(other instanceof ShotEntity){
				if(type==0){
					game.removeEntity(this);
					game.addExplosion(x-16, y-16);
					game.addExplosion(x+16, y-16);
					game.addExplosion(x-16, y+16);
					game.addExplosion(x+16, y+16);
					game.addExplosion(x-24, y-24);
					game.addExplosion(x+24, y-24);
					game.addExplosion(x-24, y+24);
					game.addExplosion(x+24, y+24);
				}else{
					//y-=30;
				}
			}else if(other instanceof ShipEntity){
				switch(type){
				case 0:
				case 1:
				default: {
					((ShipEntity) other).addLife();
					game.hud.Message("Life added!", 3);
					break;
				}
				case 2:{
					game.ammo+=300;
					game.hud.Message("Ammo added (+300)!", 3);
					break;
				}
				case 3:{
					game.bonus="double";
					game.bonusTime=6000;
					game.hud.Message("Double fire ", 3);
					break;
				}
				case 4:{
					game.bonus="tripple";
					game.bonusTime=6000;
					game.hud.Message("Tripple fire", 3);
					break;
				}
				case 5:{
					game.bonus="cannon";
					game.bonusTime=10000;
					game.hud.Message("Cannon ", 3);
					break;
				}
				case 6:{
					game.bonus="shield";
					game.bonusTime=10000;
					game.hud.Message("Shield", 3);
					break;
				}
				case 7:{
					game.bonus="laser";
					game.bonusTime=5000;
					game.hud.Message("Laser ", 3);
					break;
				}
				}
				
				game.removeEntity(this);
				
			}
		}
	}
	
}
