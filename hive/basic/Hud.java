package hive.basic;

import java.awt.Graphics;
import java.awt.Color;
public class Hud {
	Game game;
	private String string;
	private String[] strs={"","",""};
	private String[] huds={"","","","",""};
	private long display=0;
	private int x;
	private int y;
	private int viewOption;
	private boolean visible=true;
	
	public Hud(Game game,int vO,int x,int y){
		this.game=game;
		this.string="";
		this.display=0;
		this.x=5;
		this.y=game.getHeight()-15;
		this.viewOption=vO;
		this.strs[0]="";
		this.strs[1]="";
		this.strs[2]="";
	}
	
	public void draw(Graphics g){
		if(visible){
			if(viewOption==0){
				if(display>0){
					g.setColor(Color.white);
					g.drawString(string, x, y);
				}
			}else{
				g.setColor(Color.white);
				g.drawString(strs[0], x, y);
				g.drawString(strs[1], x, y-12);
				g.drawString(strs[2], x, y-24);
			}
			for(int i = 0;i<huds.length;i++){
				g.drawString(huds[i],game.getWidth()-g.getFontMetrics().stringWidth(huds[i])-5,game.getHeight()-12*(huds.length-i)-5);
			}
		}
	}
	public void Message(String str,int disp){
		if(viewOption==0){
			this.string=str;
			this.display=disp*1000;
		}else{
			this.strs[2]=this.strs[1];
			this.strs[1]=this.strs[0];
			this.strs[0]= new String(str);
		}
	}
	public void clear(){
		this.strs[0]="";
		this.strs[1]="";
		this.strs[2]="";
		this.huds[0]="";
		this.huds[1]="";
		this.huds[2]="";
		this.huds[3]="";
		this.huds[4]="";
	
	}
	
	public void update(long delta){
		display-=delta;
		huds[0]="Life: "+game.ship.getLife();
		huds[1]="Ammo:"+game.ammo;
		huds[2]="Score: "+game.score;
		huds[3]="Time: "+(System.currentTimeMillis()-game.stats.timeBegan)/1000;
		huds[4]="Bonus: "+((double)game.bonusTime/1000);
	}

}
