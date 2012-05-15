package hive.basic;

import java.util.Random;

public class AlienBasic extends Entity {
	private double moveSpeed = 90;
	private Game game;
	private Spawn spawn;
	protected Sprite[] frames = new Sprite[4];
	private long lastFrameChange;
	private long frameDuration = 50;
	private int frameNumber;
	private static String spritePath="sprites/anAlien";
	protected int worth=5;
    private int dy_const=40;
    protected int id=0;
    private double speedUp=1;
    private double speedBonus;
    
    protected int life=4;  //number of lives
	
	public AlienBasic(Game game, Spawn spawn, String ref,int x,int y) {
		super(spritePath+ref+"0.gif",x,y);
		Random rand = new Random();
		frames[0] = sprite;
		frames[1] = SpriteStore.get().getSprite("sprites/anAlien"+ref+"1"+".gif");
		frames[2] = SpriteStore.get().getSprite("sprites/anAlien"+ref+"2"+".gif");
		frames[3] = SpriteStore.get().getSprite("sprites/anAlien"+ref+"1"+".gif");
		
		this.game = game;
		this.spawn=spawn;
		speedBonus=rand.nextDouble()*dy_const;
		moveSpeed*=game.relativeSpeedConst;
		dy = (moveSpeed+speedBonus)*speedUp;
	}
	public int getLife(){
		return life;
	}
	public void update(long delta) {
		lastFrameChange += delta;
		if (lastFrameChange > frameDuration) {
			lastFrameChange = 0;
			
			frameNumber++;
			if (frameNumber >= frames.length) {
				frameNumber = 0;
			}
			sprite = frames[frameNumber];
		}
		this.speedUp=1+game.speedIncrease;
		dy = (moveSpeed+speedBonus)*speedUp;
		super.update(delta);
		if (y > game.getHeight()) 
            y=0;
	}
	
	public void collidedWith(Entity other) {

	}
	public void selfDestruct(){
		used=true;
		game.removeEntity(this);
		spawn.alienCount-=1;
		game.stats.aliensKilled[id]++;
	}
}