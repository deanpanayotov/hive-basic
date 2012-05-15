package hive.basic;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Spawn {
	private Game game;
	private int alienStartCount;
	private int alienRegCount;
	protected int alienCount;
	private long spawnMaxInterval;
	private long spawnDemandInterval;
	private long countIncreaseTime;
	private long nextIncrease;
	private long spawnTime;
	private int type;
	
	public Spawn(Game game,int aSC,long sDI,long cIT,boolean initialize,int type){
		this.game=game;
		this.type=type;
		alienStartCount=aSC;
		alienRegCount=alienStartCount;
		spawnDemandInterval=sDI*1000;
		spawnMaxInterval=spawnDemandInterval*7;
		countIncreaseTime=cIT*1000;
		nextIncrease=countIncreaseTime;
		if(initialize) init();
	}
	public void init(){
		alienCount = 0;
		for (int i=0;i<alienStartCount-1;i++){
			spawn();
		}
	}
	public void update(long delta){
		Random rand=new Random();
		spawnTime-=delta;
		nextIncrease-=delta;
		if(alienCount<alienRegCount && spawnTime>spawnDemandInterval){
			spawnTime= (long) (rand.nextDouble()*spawnDemandInterval);
		}
		if(spawnTime<=0){
			spawn();
			if(alienCount<alienRegCount){
				spawnTime= (long) (rand.nextDouble()*spawnDemandInterval);
			}else{
				spawnTime= (long) (spawnMaxInterval+rand.nextDouble()*spawnDemandInterval);
			}
		}
		if(nextIncrease<=0){
			alienRegCount++;
			nextIncrease=countIncreaseTime;
		}
	}
	public void spawn(){
		Random rand=new Random();
		Entity en=null;
		switch(type){
		case 0:{ en = new Bonus(this.game,this,  "sprites/barrel.gif",
				(int)(rand.nextDouble()*game.getWidth()),(int)(rand.nextDouble()*game.getHeight()*(-1)));
				break;
		}
		case 1:{ en = new AlienBasic(this.game,this,  "Blu",
				(int)(rand.nextDouble()*game.getWidth()),(int)(rand.nextDouble()*game.getHeight()*(-1)));
				break;
		}
		case 2:{ en = new AlienRunning(this.game,this,  "Red",
				(int)(rand.nextDouble()*game.getWidth()),(int)(rand.nextDouble()*game.getHeight()*(-1)));
				break;
		}
		case 3:{ en = new AlienAiming(this.game,this,  "Pur",
				(int)(rand.nextDouble()*game.getWidth()),(int)(rand.nextDouble()*game.getHeight()*(-1)));
				break;
		}
		case 4:{ en = new AlienSpeeding(this.game,this,  "Gre",
				(int)(rand.nextDouble()*game.getWidth()),(int)(rand.nextDouble()*game.getHeight()*(-1)));
				break;
		}
		case 5:{ en = new AlienTank(this.game,this,  "Yel",
				(int)(rand.nextDouble()*game.getWidth()),(int)(rand.nextDouble()*game.getHeight()*(-1)));
				break;
		}
		}
		game.entities.add(en);
		alienCount++;
	}
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.drawString("Aliens:"+alienCount, 5, 5);
		g.drawString("Spawns in:"+spawnTime, 5, 15);
		g.drawString("a reg count:"+alienRegCount, 5, 25);
		g.drawString("nextIncrease:"+nextIncrease, 5, 35);
	}
}
