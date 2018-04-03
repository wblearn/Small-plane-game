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
	public static final int WIDTH = 400; // ����
	public static final int HEIGHT = 654; // ����
	/** ��Ϸ�ĵ�ǰ״̬: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;

	private int score = 0; // �÷�
	private Timer timer; // ��ʱ��
	private int intervel = 1000 / 100; // ʱ����(����)

	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage gameover;

	private FlyingObject[] flyings = {}; // �л�����
	private Bullet[] bullets = {}; // �ӵ�����
	private Hero hero = new Hero(); // Ӣ�ۻ�

	static { // ��̬����飬��ʼ��ͼƬ��Դ
		try {
			background = ImageIO.read(ShootGame.class
					.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			airplane = ImageIO
					.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));//�����д�
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO
					.read(ShootGame.class.getResource("gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** �� */
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // ������ͼ
		paintHero(g); // ��Ӣ�ۻ�
		paintBullets(g); // ���ӵ�
		paintFlyingObjects(g); // ��������
		paintScore(g); // ������
		paintState(g); // ����Ϸ״̬
	}

	/** ��Ӣ�ۻ� */
	public void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}

	/** ���ӵ� */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
					null);
		}
	}

	/** �������� */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}

	/** ������ */
	public void paintScore(Graphics g) {
		int x = 10; // x����
		int y = 25; // y����
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // ����
		g.setColor(new Color(0xFF0000));
		g.setFont(font); // ��������
		g.drawString("SCORE:" + score, x, y); // ������
		y=y+20; // y������20
		g.drawString("LIFE:" + hero.getLife(), x, y); // ����
	}

	/** ����Ϸ״̬ */
	public void paintState(Graphics g) {
		switch (state) {
		case START: // ����״̬
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: // ��ͣ״̬
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER: // ��Ϸ��ֹ״̬
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		String str = null;
		if(str.equals("0"))
		{
			System.out.println(str);
		}
		JFrame frame = new JFrame("Fly");
		ShootGame game = new ShootGame(); // ������
		frame.add(game); // �������ӵ�JFrame��
		frame.setSize(WIDTH, HEIGHT); // ���ô�С
		frame.setAlwaysOnTop(true); // ��������������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ĭ�Ϲرղ���
		frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // ���ô����ͼ��
		frame.setLocationRelativeTo(null); // ���ô����ʼλ��
		frame.setVisible(true); // �������paint

		game.action(); // ����ִ��
	}

	/** ����ִ�д��� */
	public void action() {
		// �������¼�
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) { // ����ƶ�
				if (state == RUNNING) { // ����״̬���ƶ�Ӣ�ۻ�--�����λ��
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { // ������
				if (state == PAUSE) { // ��ͣ״̬������
					state = RUNNING;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) { // ����˳�
				if (state == RUNNING) { // ��Ϸδ��������������Ϊ��ͣ
					state = PAUSE;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) { // �����
				switch (state) {
				case START:
					state = RUNNING; // ����״̬������
					break;
				case GAME_OVER: // ��Ϸ�����������ֳ�
					flyings = new FlyingObject[0]; // ��շ�����
					bullets = new Bullet[0]; // ����ӵ�
					hero = new Hero(); // ���´���Ӣ�ۻ�
					score = 0; // ��ճɼ�
					state = START; // ״̬����Ϊ����
					break;
				}
			}
		};
		this.addMouseListener(l); // �������������
		this.addMouseMotionListener(l); // ������껬������

		timer = new Timer(); // �����̿���
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) { // ����״̬
					enterAction(); // �������볡
					stepAction(); // ��һ��
					shootAction(); // Ӣ�ۻ����
					bangAction(); // �ӵ��������
					outOfBoundsAction(); // ɾ��Խ������Ｐ�ӵ�
					checkGameOverAction(); // �����Ϸ����
				}
				repaint(); // �ػ棬����paint()����
			}

		}, intervel, intervel);
	}

	int flyEnteredIndex = 0; // �������볡����

	/** �������볡 */
	public void enterAction() {
		flyEnteredIndex++;
		if (flyEnteredIndex % 40 == 0) { // 400��������һ��������--10*40
			FlyingObject obj = nextOne(); // �������һ��������
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}

	/** ��һ�� */
	public void stepAction() {
		for (int i = 0; i < flyings.length; i++) { // ��������һ��
			FlyingObject f = flyings[i];
			f.step();
		}

		for (int i = 0; i < bullets.length; i++) { // �ӵ���һ��
			Bullet b = bullets[i];
			b.step();
		}
		hero.step(); // Ӣ�ۻ���һ��
	}

	/** ��������һ�� */
	public void flyingStepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}

	int shootIndex = 0; // �������

	/** ��� */
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) { // 300���뷢һ��
			Bullet[] bs = hero.shoot(); // Ӣ�۴���ӵ�
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
					bs.length); // ׷������
		}
	}

	/** �ӵ����������ײ��� */
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
			Bullet b = bullets[i];
			bang(b); // �ӵ��ͷ�����֮�����ײ���
		}
	}

	/** ɾ��Խ������Ｐ�ӵ� */
	public void outOfBoundsAction() {
		int index = 0; // ����
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ���ŵķ�����
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (!f.outOfBounds()) {
				flyingLives[index++] = f; // ��Խ�������
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // ����Խ��ķ����ﶼ����

		index = 0; // ��������Ϊ0
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // ����Խ����ӵ�����
	}

	/** �����Ϸ���� */
	public void checkGameOverAction() {
		if (isGameOver()==true) {
			state = GAME_OVER; // �ı�״̬
		}
	}

	/** �����Ϸ�Ƿ���� */
	public boolean isGameOver() {
		
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (hero.hit(obj)) { // ���Ӣ�ۻ���������Ƿ���ײ
				hero.subtractLife(); // ����
				hero.setDoubleFire(0); // ˫���������
				index = i; // ��¼���ϵķ���������
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t; // ���ϵ������һ�������ｻ��

				flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����ϵķ�����
			}
		}
		
		return hero.getLife() <= 0;
	}

	/** �ӵ��ͷ�����֮�����ײ��� */
	public void bang(Bullet bullet) {
		int index = -1; // ���еķ���������
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if (obj.shootBy(bullet)) { // �ж��Ƿ����
				index = i; // ��¼�����еķ����������
				break;
			}
		}
		if (index != -1) { // �л��еķ�����
			FlyingObject one = flyings[index]; // ��¼�����еķ�����

			FlyingObject temp = flyings[index]; // �����еķ����������һ�������ｻ��
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;

			flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����һ��������(�������е�)

			// ���one������(���˼ӷ֣�������ȡ)
			if (one instanceof Enemy) { // ������ͣ��ǵ��ˣ���ӷ�
				Enemy e = (Enemy) one; // ǿ������ת��
				score += e.getScore(); // �ӷ�
			} else { // ��Ϊ���������ý���
				Award a = (Award) one;
				int type = a.getType(); // ��ȡ��������
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire(); // ����˫������
					break;
				case Award.LIFE:
					hero.addLife(); // ���ü���
					break;
				}
			}
		}
	}

	/**
	 * ������ɷ�����
	 * 
	 * @return ���������
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
