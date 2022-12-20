package com.tarena.fly;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	public static final int WIDTH = 400; // 锟斤拷锟斤拷
	public static final int HEIGHT = 654; // 锟斤拷锟斤拷
	/** 锟斤拷戏锟侥碉拷前状态: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;

	private int score = 0; // 锟矫凤拷
	private Timer timer; // 锟斤拷时锟斤拷
	private int intervel = 1000 / 100; // 时锟斤拷锟斤拷(锟斤拷锟斤拷)

	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage gameover;

	private FlyingObject[] flyings = {}; // 锟叫伙拷锟斤拷锟斤拷
	private Bullet[] bullets = {}; // 锟接碉拷锟斤拷锟斤拷
	private Hero hero = new Hero(); // 英锟桔伙拷
	

	static { // 锟斤拷态锟斤拷锟斤拷椋拷锟绞硷拷锟酵计拷锟皆�
		try {
			background = ImageIO.read(ShootGame.class
					.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			airplane = ImageIO
					.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));//锟斤拷锟斤拷锟叫达拷
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO
					.read(ShootGame.class.getResource("gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 锟斤拷 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // 锟斤拷锟斤拷锟斤拷图
		paintHero(g); // 锟斤拷英锟桔伙拷
		paintBullets(g); // 锟斤拷锟接碉拷
		paintFlyingObjects(g); // 锟斤拷锟斤拷锟斤拷锟斤拷
		paintScore(g); // 锟斤拷锟斤拷锟斤拷
		paintState(g); // 锟斤拷锟斤拷戏状态
	}

	/** 锟斤拷英锟桔伙拷 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}

	/** 锟斤拷锟接碉拷 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
					null);
		}
	}

	/** 锟斤拷锟斤拷锟斤拷锟斤拷 */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}

	/** 锟斤拷锟斤拷锟斤拷 */
	public void paintScore(Graphics g) {
		int x = 10; // x锟斤拷锟斤拷
		int y = 25; // y锟斤拷锟斤拷
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 锟斤拷锟斤拷
		g.setColor(new Color(0xFF0000));
		g.setFont(font); // 锟斤拷锟斤拷锟斤拷锟斤拷
		g.drawString("SCORE:" + score, x, y); // 锟斤拷锟斤拷锟斤拷
		y=y+20; // y锟斤拷锟斤拷锟斤拷20
		g.drawString("LIFE:" + hero.getLife(), x, y); // 锟斤拷锟斤拷
	}

	/** 锟斤拷锟斤拷戏状态 */
	public void paintState(Graphics g) {
		switch (state) {
		case START: // 锟斤拷锟斤拷状态
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: // 锟斤拷停状态
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER: // 锟斤拷戏锟斤拷止状态
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		int modify = 1;
		String str = "2";
		if(str.equals("0"))
		{
			System.out.println("str");
		}
		JFrame frame = new JFrame("Fly");
		ShootGame game = new ShootGame(); // 锟斤拷锟斤拷锟斤拷
		frame.add(game); // 锟斤拷锟斤拷锟斤拷锟接碉拷JFrame锟斤拷
		frame.setSize(WIDTH, HEIGHT); // 锟斤拷锟矫达拷小
		frame.setAlwaysOnTop(true); // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默锟较关闭诧拷锟斤拷
		frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 锟斤拷锟矫达拷锟斤拷锟酵硷拷锟�
		frame.setLocationRelativeTo(null); // 锟斤拷锟矫达拷锟斤拷锟绞嘉伙拷锟�
		frame.setVisible(true); // 锟斤拷锟斤拷锟斤拷锟絧aint

		game.action(); // 锟斤拷锟斤拷执锟斤拷
	}

	/** 锟斤拷锟斤拷执锟叫达拷锟斤拷 */
	public void action() {
		// 锟斤拷锟斤拷锟斤拷锟铰硷拷
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) { // 锟斤拷锟斤拷贫锟�
				if (state == RUNNING) { // 锟斤拷锟斤拷状态锟斤拷锟狡讹拷英锟桔伙拷--锟斤拷锟斤拷锟轿伙拷锟�
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { // 锟斤拷锟斤拷锟斤拷
				if (state == PAUSE) { // 锟斤拷停状态锟斤拷锟斤拷锟斤拷
					state = RUNNING;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) { // 锟斤拷锟斤拷顺锟�
				if (state == RUNNING) { // 锟斤拷戏未锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷为锟斤拷停
					state = PAUSE;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) { // 锟斤拷锟斤拷锟�
				switch (state) {
				case START:
					state = RUNNING; // 锟斤拷锟斤拷状态锟斤拷锟斤拷锟斤拷
					break;
				case GAME_OVER: // 锟斤拷戏锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街筹拷
					flyings = new FlyingObject[0]; // 锟斤拷辗锟斤拷锟斤拷锟�
					bullets = new Bullet[0]; // 锟斤拷锟斤拷拥锟�
					hero = new Hero(); // 锟斤拷锟铰达拷锟斤拷英锟桔伙拷
					score = 0; // 锟斤拷粘杉锟�
					state = START; // 状态锟斤拷锟斤拷为锟斤拷锟斤拷
					break;
				}
			}
		};
		this.addMouseListener(l); // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
		this.addMouseMotionListener(l); // 锟斤拷锟斤拷锟斤拷昊拷锟斤拷锟斤拷锟�

		timer = new Timer(); // 锟斤拷锟斤拷锟教匡拷锟斤拷
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) { // 锟斤拷锟斤拷状态
					enterAction(); // 锟斤拷锟斤拷锟斤拷锟诫场
					stepAction(); // 锟斤拷一锟斤拷
					shootAction(); // 英锟桔伙拷锟斤拷锟�
					bangAction(); // 锟接碉拷锟斤拷锟斤拷锟斤拷锟�
					outOfBoundsAction(); // 删锟斤拷越锟斤拷锟斤拷锟斤拷锛帮拷拥锟�
					checkGameOverAction(); // 锟斤拷锟斤拷锟较凤拷锟斤拷锟�
				}
				repaint(); // 锟截绘，锟斤拷锟斤拷paint()锟斤拷锟斤拷
			}

		}, intervel, intervel);
	}

	int flyEnteredIndex = 0; // 锟斤拷锟斤拷锟斤拷锟诫场锟斤拷锟斤拷

	/** 锟斤拷锟斤拷锟斤拷锟诫场 */
	public void enterAction() {
		flyEnteredIndex++;
		if (flyEnteredIndex % 40 == 0) { // 400锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷--10*40
			FlyingObject obj = nextOne(); // 锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锟�
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}

	/** 锟斤拷一锟斤拷 */
	public void stepAction() {
		for (int i = 0; i < flyings.length; i++) { // 锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷
			FlyingObject f = flyings[i];
			f.step();
		}

		for (int i = 0; i < bullets.length; i++) { // 锟接碉拷锟斤拷一锟斤拷
			Bullet b = bullets[i];
			b.step();
		}
		hero.step(); // 英锟桔伙拷锟斤拷一锟斤拷
	}

	/** 锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷 */
	public void flyingStepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}

	int shootIndex = 0; // 锟斤拷锟斤拷锟斤拷锟�

	/** 锟斤拷锟� */
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) { // 300锟斤拷锟诫发一锟斤拷
			Bullet[] bs = hero.shoot(); // 英锟桔达拷锟斤拷拥锟�
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 锟斤拷锟斤拷
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
					bs.length); // 追锟斤拷锟斤拷锟斤拷
		}
	}

	/** 锟接碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟阶诧拷锟斤拷 */
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { // 锟斤拷锟斤拷锟斤拷锟斤拷锟接碉拷
			Bullet b = bullets[i];
			bang(b); // 锟接碉拷锟酵凤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟阶诧拷锟斤拷
		}
	}

	/** 删锟斤拷越锟斤拷锟斤拷锟斤拷锛帮拷拥锟� */
	public void outOfBoundsAction() {
		int index = 0; // 锟斤拷锟斤拷
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 锟斤拷锟脚的凤拷锟斤拷锟斤拷
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (!f.outOfBounds()) {
				flyingLives[index++] = f; // 锟斤拷越锟斤拷锟斤拷锟斤拷锟�
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // 锟斤拷锟斤拷越锟斤拷姆锟斤拷锟斤拷锒硷拷锟斤拷锟�

		index = 0; // 锟斤拷锟斤拷锟斤拷锟斤拷为0
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // 锟斤拷锟斤拷越锟斤拷锟斤拷拥锟斤拷锟斤拷锟�
	}

	/** 锟斤拷锟斤拷锟较凤拷锟斤拷锟� */
	public void checkGameOverAction() {
		if (isGameOver()==true) {
			state = GAME_OVER; // 锟侥憋拷状态
		}
	}

	/** 锟斤拷锟斤拷锟较凤拷欠锟斤拷锟斤拷 */
	public boolean isGameOver() {
		
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (hero.hit(obj)) { // 锟斤拷锟接拷刍锟斤拷锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷撞
				hero.subtractLife(); // 锟斤拷锟斤拷
				hero.setDoubleFire(0); // 双锟斤拷锟斤拷锟斤拷锟斤拷锟�
				index = i; // 锟斤拷录锟斤拷锟较的凤拷锟斤拷锟斤拷锟斤拷锟斤拷
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t; // 锟斤拷锟较碉拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锝伙拷锟�

				flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删锟斤拷锟斤拷锟较的凤拷锟斤拷锟斤拷
			}
		}
		
		return hero.getLife() <= 0;
	}

	/** 锟接碉拷锟酵凤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟阶诧拷锟斤拷 */
	public void bang(Bullet bullet) {
		int index = -1; // 锟斤拷锟叫的凤拷锟斤拷锟斤拷锟斤拷锟斤拷
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if (obj.shootBy(bullet)) { // 锟叫讹拷锟角凤拷锟斤拷锟�
				index = i; // 锟斤拷录锟斤拷锟斤拷锟叫的凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
				break;
			}
		}
		if (index != -1) { // 锟叫伙拷锟叫的凤拷锟斤拷锟斤拷
			FlyingObject one = flyings[index]; // 锟斤拷录锟斤拷锟斤拷锟叫的凤拷锟斤拷锟斤拷

			FlyingObject temp = flyings[index]; // 锟斤拷锟斤拷锟叫的凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锝伙拷锟�
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;

			flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锟�(锟斤拷锟斤拷锟斤拷锟叫碉拷)

			// 锟斤拷锟給ne锟斤拷锟斤拷锟斤拷(锟斤拷锟剿加分ｏ拷锟斤拷锟斤拷锟斤拷取)
			if (one instanceof Enemy) { // 锟斤拷锟斤拷锟斤拷停锟斤拷堑锟斤拷耍锟斤拷锟接凤拷
				Enemy e = (Enemy) one; // 强锟斤拷锟斤拷锟斤拷转锟斤拷
				score += e.getScore(); // 锟接凤拷
			} else { // 锟斤拷为锟斤拷锟斤拷锟斤拷锟斤拷锟矫斤拷锟斤拷
				Award a = (Award) one;
				int type = a.getType(); // 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire(); // 锟斤拷锟斤拷双锟斤拷锟斤拷锟斤拷
					break;
				case Award.LIFE:
					hero.addLife(); // 锟斤拷锟矫硷拷锟斤拷
					break;
				}
			}
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷煞锟斤拷锟斤拷锟�
	 * 
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 */
	public static FlyingObject nextOne() {
		Random random = new Random();
		int type = random.nextInt(20); // [0,20)
		if (type < 4) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

}
