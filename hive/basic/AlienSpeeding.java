package hive.basic;

public class AlienSpeeding extends AlienBasic {
	private double moveSpeed = 240;
	private Game game;
	public AlienSpeeding(Game game,Spawn spawn, String ref,int x,int y) {
		super(game,spawn, ref,x,y);
				
		this.game=game;
		life=5;
		dy=moveSpeed;
		worth=9;
		id=3;
	}
}
