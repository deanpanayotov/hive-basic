package hive.basic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

public class Game extends Canvas {
	private BufferStrategy strategy;

	private boolean gameRunning = true;

	protected ArrayList entities = new ArrayList();
	private ArrayList removeList = new ArrayList();
	protected ArrayList spawns = new ArrayList();

	protected ShipEntity ship;
	
	protected Hud hud;
	protected Statistics stats;
	
	private double moveSpeed = 300;
	private long lastFire = 0;
	private long firingInterval = 80;
	
	protected int score = 0;
	protected int ammo = 300;
	
	private double speedUp=0.0041;
	private long speedUpInterval=1000;
	private long nextSpeedUp=speedUpInterval;
	protected double speedIncrease=0;
	protected double relativeSpeedConst=1.2;
	
	protected String bonus = "";
	protected long bonusTime = 0;

	private String message = "";
	private boolean waitingForKeyPress = true;
	private boolean submitScore=false;
	private boolean askName=false;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean firePressed = false;

	// CONSTRUCTOR//
	public Game() {

		JFrame container = new JFrame("HiveBasic");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(null);

		setBounds(0, 0, 800, 600);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addKeyListener(new KeyInputHandler());

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		hud = new Hud(this, 1, 5, getHeight() - 15);
		stats= new Statistics(this);
		initEntities();
	}

	private void startGame() {
		entities.clear();
		spawns.clear();
		hud.clear();
		stats.clear();
		System.gc();
		clearInput();
		clearData();
		stats.timeBegan=System.currentTimeMillis();
		initEntities();
	}

	private void initEntities() {

		ship = new ShipEntity(this, "sprites/ship.gif", 370, 550);
		entities.add(ship);
		Spawn spawn;
		spawn = new Spawn(this, 1, 6, 0, true, 0);
		spawns.add(spawn);
		spawn = new Spawn(this, 5, 3, 15, true, 1);
		spawns.add(spawn);
		spawn = new Spawn(this, 5, 3, 15, true, 2);
		spawns.add(spawn);
		spawn = new Spawn(this, 4, 5, 20, true, 3);
		spawns.add(spawn);
		spawn = new Spawn(this, 3, 5, 20, true, 4);
		spawns.add(spawn);
		spawn = new Spawn(this, 1, 10, 60, true, 5);
		spawns.add(spawn);

	}

	public void clearInput() {
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}
	
	public void clearData(){
		score=0;
		ammo=300;
		bonus="";
		lastFire=0;
		bonusTime=0;
	}

	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}

	public void notifyDeath() {
		submitScore=true;
		askName=true;
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
		stats.timeEnded=System.currentTimeMillis();
	}

	public void restart() {
		message = "Game restarted.";
		waitingForKeyPress = true;
	}

	public void tryToFire() {
		if ((System.currentTimeMillis() - lastFire < firingInterval)   || ammo == 0) {
			return;
		}
		lastFire = System.currentTimeMillis();
		
		switch (bonus) {
		default: {
			ShotEntity shot = new ShotEntity(this, "sprites/missile.png",
					ship.getX() + 15, ship.getY() - 15);
			entities.add(0, shot);
			break;
		}
		case "double": {
			ShotEntity shot = new ShotEntity(this, "sprites/missile.png",
					ship.getX() + 3, ship.getY() - 15);
			entities.add(0, shot);
			shot = new ShotEntity(this, "sprites/missile.png",
					ship.getX() + 29, ship.getY() - 15);
			entities.add(0, shot);
			break;
		}
		case "tripple": {
			ShotEntity shot = new ShotEntity(this, "sprites/missile.png",
					ship.getX() + 1, ship.getY() - 15);
			entities.add(0, shot);
			shot = new ShotEntity(this, "sprites/missile.png",
					ship.getX() + 32, ship.getY() - 15);
			entities.add(0, shot);
			shot = new ShotEntity(this, "sprites/missile.png",
					ship.getX() + 15, ship.getY() - 15);
			entities.add(0, shot);
			break;
		}
		case "cannon":{
			CannonBall shot = new CannonBall(this, "sprites/shot.gif",
					ship.getX() + 8, ship.getY() - 15);
			entities.add(0, shot);
			break;
		}
		case "shield":{
			ShieldEntity shield = new ShieldEntity(this, "sprites/turret0.gif",
					  ship.getX() , ship.getY()); 
					  entities.add(0, shield);
			
			break;
		}
		case "laser":{
			
			  LaserEntity laser = new LaserEntity(this, "sprites/missile.png",
			  ship.getX() + 10, ship.getY() - 30); 
			  entities.add(0, laser);	 
			break;
		}
		}
	}

	public void addExplosion(double x, double y) {
		Entity expl = new Explosion(this, "sprites/Explode.gif",
				(int) (x - 16), (int) (y - 16));
		entities.add(expl);
	}
	
	public void updateBonus(long delta) {
		if (bonusTime > 0) {
			bonusTime -= delta;
		} else {
			bonus = "";
		}
	}
	
	public String getName(int score){
		String content="You scored "+score+"!\nWhat's your name?";
		JFrame frame = new JFrame();
		String name = JOptionPane.showInputDialog(frame, content);
		frame.dispose();
		return name;
	}
	
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();

		while (gameRunning) {
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, 800, 600);
			
			if (!waitingForKeyPress) {

				for (int i = 0; i < entities.size(); i++) {
					Entity entity = (Entity) entities.get(i);

					entity.update(delta);
					entity.draw(g);
				}
				for (int i = 0; i < spawns.size(); i++) {
					Spawn spawn = (Spawn) spawns.get(i);
					spawn.update(delta);
					spawn.draw(g);
				}
				updateBonus(delta);
				hud.update(delta);
				hud.draw(g);

				//**refactor to bruteforce if needed
				for (int p = 0; p < entities.size(); p++) {
					for (int s = p + 1; s < entities.size(); s++) {
						Entity me = (Entity) entities.get(p);
						Entity him = (Entity) entities.get(s);
	
						if (me.collidesWith(him, g)) {
							me.collidedWith(him);
							him.collidedWith(me);
						}
					}
				}
				
				nextSpeedUp-=delta;
				if(nextSpeedUp<=0){
					speedIncrease+=speedUp;
					nextSpeedUp=speedUpInterval;
				}
				
				entities.removeAll(removeList);
				removeList.clear();
			}

			if (waitingForKeyPress) {
				
				g.setColor(Color.white);
	
				if(submitScore){
					if(askName){
						getName(score);
						askName=false;
						stats.formOutput();
					}
					stats.display(g);
				}
				
				g.drawString(message,
						(800 - g.getFontMetrics().stringWidth(message)) / 2,
						100);
				g.drawString("Press any key", (800 - g.getFontMetrics()
						.stringWidth("Press any key")) / 2, 150);
			}

			g.dispose();
			strategy.show();

			ship.setHorizontalMovement(0);
			ship.setVerticalMovement(0);

			if ((leftPressed) && (!rightPressed)) {
				ship.setHorizontalMovement(-moveSpeed);
			} else if ((rightPressed) && (!leftPressed)) {
				ship.setHorizontalMovement(moveSpeed);
			}
			if ((upPressed) && (!downPressed)) {
				ship.setVerticalMovement(-moveSpeed);
			} else if ((downPressed) && (!upPressed)) {
				ship.setVerticalMovement(moveSpeed);
			}
			if (firePressed) {
				tryToFire();
			}

			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}
	}

	
	private class KeyInputHandler extends KeyAdapter {
		private int pressCount = 1;
		
		public void keyPressed(KeyEvent e) {
			if (waitingForKeyPress) {
				return;
			}

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: {
				leftPressed = true;
				break;
			}
			case KeyEvent.VK_RIGHT: {
				rightPressed = true;
				break;
			}
			case KeyEvent.VK_X: {
				firePressed = true;
				break;
			}
			case KeyEvent.VK_UP: {
				upPressed = true;
				break;
			}
			case KeyEvent.VK_DOWN: {
				downPressed = true;
				break;
			}
			case KeyEvent.VK_R: {
				restart();
				break;
			}
			case KeyEvent.VK_Q: {
				getName(score);
				break;
			}

			}
		}

		public void keyReleased(KeyEvent e) {
			if (waitingForKeyPress) {
				return;
			}
			
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: {
				leftPressed = false;
				break;
			}
			case KeyEvent.VK_RIGHT: {
				rightPressed = false;
				break;
			}
			case KeyEvent.VK_X: {
				firePressed = false;
				break;
			}
			case KeyEvent.VK_UP: {
				upPressed = false;
				break;
			}
			case KeyEvent.VK_DOWN: {
				downPressed = false;
				break;
			}
			case KeyEvent.VK_R: {
				break;
			}

			}
		}

		public void keyTyped(KeyEvent e) {
			if (waitingForKeyPress) {
				if (pressCount == 1) {
					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}

			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}

	public static void main(String argv[]) {
		Game g = new Game();
		g.gameLoop();
		//test
	}
}
