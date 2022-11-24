![](http://wblearn.github.io/img/in-post/public/2556999-1a2ad6a9a6690588.gif)

## 写在前面

技术源于分享，所以今天抽空把自己之前用java做过的小游戏整理贴出来给大家参考学习。java确实不适合写桌面应用，这里只是**通过这个游戏让大家理解oop面向对象编程的过程**，纯属娱乐。代码写的很简单，也很容易理解，并且注释写的很清楚了，还有问题，自己私下去补课学习。

## 完整代码

>敌飞机


```
import java.util.Random;


 敌飞机: 是飞行物，也是敌人
 
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;  //移动步骤
	
	/** 初始化数据 */
	public Airplane(){
		this.image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;          
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
	}
	
	/** 获取分数 */
	@Override
	public int getScore() {  
		return 5;
	}

	/** //越界处理 */
	@Override
	public 	boolean outOfBounds() {   
		return y>ShootGame.HEIGHT;
	}

	/** 移动 */
	@Override
	public void step() {   
		y += speed;
	}

}

```

>分数奖励

```
/** 
 * 奖励 
 */  
public interface Award {  
    int DOUBLE_FIRE = 0;  //双倍火力  
    int LIFE = 1;   //1条命  
    /** 获得奖励类型(上面的0或1) */  
    int getType();  
}  
```

>蜜蜂

```
import java.util.Random;  
  
/** 蜜蜂 */  
public class Bee extends FlyingObject implements Award{  
    private int xSpeed = 1;   //x坐标移动速度  
    private int ySpeed = 2;   //y坐标移动速度  
    private int awardType;    //奖励类型  
      
    /** 初始化数据 */  
    public Bee(){  
        this.image = ShootGame.bee;  
        width = image.getWidth();  
        height = image.getHeight();  
        y = -height;  
        Random rand = new Random();  
        x = rand.nextInt(ShootGame.WIDTH - width);  
        awardType = rand.nextInt(2);   //初始化时给奖励  
    }  
      
    /** 获得奖励类型 */  
    public int getType(){  
        return awardType;  
    }  
  
    /** 越界处理 */  
    @Override  
    public boolean outOfBounds() {  
        return y>ShootGame.HEIGHT;  
    }  
  
    /** 移动，可斜着飞 */  
    @Override  
    public void step() {        
        x += xSpeed;  
        y += ySpeed;  
        if(x > ShootGame.WIDTH-width){    
            xSpeed = -1;  
        }  
        if(x < 0){  
            xSpeed = 1;  
        }  
    }  
}
```

>子弹类：是飞行物体

```
/** 
 * 子弹类:是飞行物 
 */  
public class Bullet extends FlyingObject {  
    private int speed = 3;  //移动的速度  
      
    /** 初始化数据 */  
    public Bullet(int x,int y){  
        this.x = x;  
        this.y = y;  
        this.image = ShootGame.bullet;  
    }  
  
    /** 移动 */  
    @Override  
    public void step(){     
        y-=speed;  
    }  
  
    /** 越界处理 */  
    @Override  
    public boolean outOfBounds() {  
        return y<-height;  
    }  
  
}  
```

>敌人的分数

```
/** 
 * 敌人，可以有分数 
 */  
public interface Enemy {  
    /** 敌人的分数  */  
    int getScore();  
}  
```

>飞行物(敌机，蜜蜂，子弹，英雄机)

```
import java.awt.image.BufferedImage;  
  
/** 
 * 飞行物(敌机，蜜蜂，子弹，英雄机) 
 */  
public abstract class FlyingObject {  
    protected int x;    //x坐标  
    protected int y;    //y坐标  
    protected int width;    //宽  
    protected int height;   //高  
    protected BufferedImage image;   //图片  
  
    public int getX() {  
        return x;  
    }  
  
    public void setX(int x) {  
        this.x = x;  
    }  
  
    public int getY() {  
        return y;  
    }  
  
    public void setY(int y) {  
        this.y = y;  
    }  
  
    public int getWidth() {  
        return width;  
    }  
  
    public void setWidth(int width) {  
        this.width = width;  
    }  
  
    public int getHeight() {  
        return height;  
    }  
  
    public void setHeight(int height) {  
        this.height = height;  
    }  
  
    public BufferedImage getImage() {  
        return image;  
    }  
  
    public void setImage(BufferedImage image) {  
        this.image = image;  
    }  
  
    /** 
     * 检查是否出界 
     * @return true 出界与否 
     */  
    public abstract boolean outOfBounds();  
      
    /** 
     * 飞行物移动一步 
     */  
    public abstract void step();  
      
    /** 
     * 检查当前飞行物体是否被子弹(x,y)击(shoot)中 
     * @param Bullet 子弹对象 
     * @return true表示被击中了 
     */  
    public boolean shootBy(Bullet bullet){  
        int x = bullet.x;  //子弹横坐标  
        int y = bullet.y;  //子弹纵坐标  
        return this.x<x && x<this.x+width && this.y<y && y<this.y+height;  
    }  
  
}  
```

>英雄机

```
import java.awt.image.BufferedImage;  
  
/** 
 * 英雄机:是飞行物 
 */  
public class Hero extends FlyingObject{  
      
    private BufferedImage[] images = {};  //英雄机图片  
    private int index = 0;                //英雄机图片切换索引  
      
    private int doubleFire;   //双倍火力  
    private int life;   //命  
      
    /** 初始化数据 */  
    public Hero(){  
        life = 3;   //初始3条命  
        doubleFire = 0;   //初始火力为0  
        images = new BufferedImage[]{ShootGame.hero0, ShootGame.hero1}; //英雄机图片数组  
        image = ShootGame.hero0;   //初始为hero0图片  
        width = image.getWidth();  
        height = image.getHeight();  
        x = 150;  
        y = 400;  
    }  
      
    /** 获取双倍火力 */  
    public int isDoubleFire() {  
        return doubleFire;  
    }  
  
    /** 设置双倍火力 */  
    public void setDoubleFire(int doubleFire) {  
        this.doubleFire = doubleFire;  
    }  
      
    /** 增加火力 */  
    public void addDoubleFire(){  
        doubleFire = 40;  
    }  
      
    /** 增命 */  
    public void addLife(){  //增命  
        life++;  
    }  
      
    /** 减命 */  
    public void subtractLife(){   //减命  
        life--;  
    }  
      
    /** 获取命 */  
    public int getLife(){  
        return life;  
    }  
      
    /** 当前物体移动了一下，相对距离，x,y鼠标位置  */  
    public void moveTo(int x,int y){     
        this.x = x - width/2;  
        this.y = y - height/2;  
    }  
  
    /** 越界处理 */  
    @Override  
    public boolean outOfBounds() {  
        return false;    
    }  
  
    /** 发射子弹 */  
    public Bullet[] shoot(){     
        int xStep = width/4;      //4半  
        int yStep = 20;  //步  
        if(doubleFire>0){  //双倍火力  
            Bullet[] bullets = new Bullet[2];  
            bullets[0] = new Bullet(x+xStep,y-yStep);  //y-yStep(子弹距飞机的位置)  
            bullets[1] = new Bullet(x+3*xStep,y-yStep);  
            return bullets;  
        }else{      //单倍火力  
            Bullet[] bullets = new Bullet[1];  
            bullets[0] = new Bullet(x+2*xStep,y-yStep);    
            return bullets;  
        }  
    }  
  
    /** 移动 */  
    @Override  
    public void step() {  
        if(images.length>0){  
            image = images[index++/10%images.length];  //切换图片hero0，hero1  
        }  
    }  
      
    /** 碰撞算法 */  
    public boolean hit(FlyingObject other){  
          
        int x1 = other.x - this.width/2;                 //x坐标最小距离  
        int x2 = other.x + this.width/2 + other.width;   //x坐标最大距离  
        int y1 = other.y - this.height/2;                //y坐标最小距离  
        int y2 = other.y + this.height/2 + other.height; //y坐标最大距离  
      
        int herox = this.x + this.width/2;               //英雄机x坐标中心点距离  
        int heroy = this.y + this.height/2;              //英雄机y坐标中心点距离  
          
        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了  
    }  
      
}  
```

>游戏启动主类

```
import java.awt.Font;  
import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.util.Arrays;  
import java.util.Random;  
import java.util.Timer;  
import java.util.TimerTask;  
import java.awt.image.BufferedImage;  
  
import javax.imageio.ImageIO;  
import javax.swing.ImageIcon;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
  
public class ShootGame extends JPanel {  
    public static final int WIDTH = 400; // 面板宽  
    public static final int HEIGHT = 654; // 面板高  
    /** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */  
    private int state;  
    private static final int START = 0;  
    private static final int RUNNING = 1;  
    private static final int PAUSE = 2;  
    private static final int GAME_OVER = 3;  
  
    private int score = 0; // 得分  
    private Timer timer; // 定时器  
    private int intervel = 1000 / 100; // 时间间隔(毫秒)  
  
    public static BufferedImage background;  
    public static BufferedImage start;  
    public static BufferedImage airplane;  
    public static BufferedImage bee;  
    public static BufferedImage bullet;  
    public static BufferedImage hero0;  
    public static BufferedImage hero1;  
    public static BufferedImage pause;  
    public static BufferedImage gameover;  
  
    private FlyingObject[] flyings = {}; // 敌机数组  
    private Bullet[] bullets = {}; // 子弹数组  
    private Hero hero = new Hero(); // 英雄机  
  
    static { // 静态代码块，初始化图片资源  
        try {  
            background = ImageIO.read(ShootGame.class  
                    .getResource("background.png"));  
            start = ImageIO.read(ShootGame.class.getResource("start.png"));  
            airplane = ImageIO  
                    .read(ShootGame.class.getResource("airplane.png"));  
            bee = ImageIO.read(ShootGame.class.getResource("bee.png"));  
            bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));  
            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));  
            hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));  
            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));  
            gameover = ImageIO  
                    .read(ShootGame.class.getResource("gameover.png"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 画 */  
    @Override  
    public void paint(Graphics g) {  
        g.drawImage(background, 0, 0, null); // 画背景图  
        paintHero(g); // 画英雄机  
        paintBullets(g); // 画子弹  
        paintFlyingObjects(g); // 画飞行物  
        paintScore(g); // 画分数  
        paintState(g); // 画游戏状态  
    }  
  
    /** 画英雄机 */  
    public void paintHero(Graphics g) {  
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);  
    }  
  
    /** 画子弹 */  
    public void paintBullets(Graphics g) {  
        for (int i = 0; i < bullets.length; i++) {  
            Bullet b = bullets[i];  
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),  
                    null);  
        }  
    }  
  
    /** 画飞行物 */  
    public void paintFlyingObjects(Graphics g) {  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject f = flyings[i];  
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);  
        }  
    }  
  
    /** 画分数 */  
    public void paintScore(Graphics g) {  
        int x = 10; // x坐标  
        int y = 25; // y坐标  
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字体  
        g.setColor(new Color(0xFF0000));  
        g.setFont(font); // 设置字体  
        g.drawString("SCORE:" + score, x, y); // 画分数  
        y=y+20; // y坐标增20  
        g.drawString("LIFE:" + hero.getLife(), x, y); // 画命  
    }  
  
    /** 画游戏状态 */  
    public void paintState(Graphics g) {  
        switch (state) {  
        case START: // 启动状态  
            g.drawImage(start, 0, 0, null);  
            break;  
        case PAUSE: // 暂停状态  
            g.drawImage(pause, 0, 0, null);  
            break;  
        case GAME_OVER: // 游戏终止状态  
            g.drawImage(gameover, 0, 0, null);  
            break;  
        }  
    }  
  
    public static void main(String[] args) {  
        JFrame frame = new JFrame("Fly");  
        ShootGame game = new ShootGame(); // 面板对象  
        frame.add(game); // 将面板添加到JFrame中  
        frame.setSize(WIDTH, HEIGHT); // 设置大小  
        frame.setAlwaysOnTop(true); // 设置其总在最上  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作  
        frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 设置窗体的图标  
        frame.setLocationRelativeTo(null); // 设置窗体初始位置  
        frame.setVisible(true); // 尽快调用paint  
  
        game.action(); // 启动执行  
    }  
  
    /** 启动执行代码 */  
    public void action() {  
        // 鼠标监听事件  
        MouseAdapter l = new MouseAdapter() {  
            @Override  
            public void mouseMoved(MouseEvent e) { // 鼠标移动  
                if (state == RUNNING) { // 运行状态下移动英雄机--随鼠标位置  
                    int x = e.getX();  
                    int y = e.getY();  
                    hero.moveTo(x, y);  
                }  
            }  
  
            @Override  
            public void mouseEntered(MouseEvent e) { // 鼠标进入  
                if (state == PAUSE) { // 暂停状态下运行  
                    state = RUNNING;  
                }  
            }  
  
            @Override  
            public void mouseExited(MouseEvent e) { // 鼠标退出  
                if (state == RUNNING) { // 游戏未结束，则设置其为暂停  
                    state = PAUSE;  
                }  
            }  
  
            @Override  
            public void mouseClicked(MouseEvent e) { // 鼠标点击  
                switch (state) {  
                case START:  
                    state = RUNNING; // 启动状态下运行  
                    break;  
                case GAME_OVER: // 游戏结束，清理现场  
                    flyings = new FlyingObject[0]; // 清空飞行物  
                    bullets = new Bullet[0]; // 清空子弹  
                    hero = new Hero(); // 重新创建英雄机  
                    score = 0; // 清空成绩  
                    state = START; // 状态设置为启动  
                    break;  
                }  
            }  
        };  
        this.addMouseListener(l); // 处理鼠标点击操作  
        this.addMouseMotionListener(l); // 处理鼠标滑动操作  
  
        timer = new Timer(); // 主流程控制  
        timer.schedule(new TimerTask() {  
            @Override  
            public void run() {  
                if (state == RUNNING) { // 运行状态  
                    enterAction(); // 飞行物入场  
                    stepAction(); // 走一步  
                    shootAction(); // 英雄机射击  
                    bangAction(); // 子弹打飞行物  
                    outOfBoundsAction(); // 删除越界飞行物及子弹  
                    checkGameOverAction(); // 检查游戏结束  
                }  
                repaint(); // 重绘，调用paint()方法  
            }  
  
        }, intervel, intervel);  
    }  
  
    int flyEnteredIndex = 0; // 飞行物入场计数  
  
    /** 飞行物入场 */  
    public void enterAction() {  
        flyEnteredIndex++;  
        if (flyEnteredIndex % 40 == 0) { // 400毫秒生成一个飞行物--10*40  
            FlyingObject obj = nextOne(); // 随机生成一个飞行物  
            flyings = Arrays.copyOf(flyings, flyings.length + 1);  
            flyings[flyings.length - 1] = obj;  
        }  
    }  
  
    /** 走一步 */  
    public void stepAction() {  
        for (int i = 0; i < flyings.length; i++) { // 飞行物走一步  
            FlyingObject f = flyings[i];  
            f.step();  
        }  
  
        for (int i = 0; i < bullets.length; i++) { // 子弹走一步  
            Bullet b = bullets[i];  
            b.step();  
        }  
        hero.step(); // 英雄机走一步  
    }  
  
    /** 飞行物走一步 */  
    public void flyingStepAction() {  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject f = flyings[i];  
            f.step();  
        }  
    }  
  
    int shootIndex = 0; // 射击计数  
  
    /** 射击 */  
    public void shootAction() {  
        shootIndex++;  
        if (shootIndex % 30 == 0) { // 300毫秒发一颗  
            Bullet[] bs = hero.shoot(); // 英雄打出子弹  
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容  
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,  
                    bs.length); // 追加数组  
        }  
    }  
  
    /** 子弹与飞行物碰撞检测 */  
    public void bangAction() {  
        for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹  
            Bullet b = bullets[i];  
            bang(b); // 子弹和飞行物之间的碰撞检查  
        }  
    }  
  
    /** 删除越界飞行物及子弹 */  
    public void outOfBoundsAction() {  
        int index = 0; // 索引  
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 活着的飞行物  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject f = flyings[i];  
            if (!f.outOfBounds()) {  
                flyingLives[index++] = f; // 不越界的留着  
            }  
        }  
        flyings = Arrays.copyOf(flyingLives, index); // 将不越界的飞行物都留着  
  
        index = 0; // 索引重置为0  
        Bullet[] bulletLives = new Bullet[bullets.length];  
        for (int i = 0; i < bullets.length; i++) {  
            Bullet b = bullets[i];  
            if (!b.outOfBounds()) {  
                bulletLives[index++] = b;  
            }  
        }  
        bullets = Arrays.copyOf(bulletLives, index); // 将不越界的子弹留着  
    }  
  
    /** 检查游戏结束 */  
    public void checkGameOverAction() {  
        if (isGameOver()==true) {  
            state = GAME_OVER; // 改变状态  
        }  
    }  
  
    /** 检查游戏是否结束 */  
    public boolean isGameOver() {  
          
        for (int i = 0; i < flyings.length; i++) {  
            int index = -1;  
            FlyingObject obj = flyings[i];  
            if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞  
                hero.subtractLife(); // 减命  
                hero.setDoubleFire(0); // 双倍火力解除  
                index = i; // 记录碰上的飞行物索引  
            }  
            if (index != -1) {  
                FlyingObject t = flyings[index];  
                flyings[index] = flyings[flyings.length - 1];  
                flyings[flyings.length - 1] = t; // 碰上的与最后一个飞行物交换  
  
                flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除碰上的飞行物  
            }  
        }  
          
        return hero.getLife() <= 0;  
    }  
  
    /** 子弹和飞行物之间的碰撞检查 */  
    public void bang(Bullet bullet) {  
        int index = -1; // 击中的飞行物索引  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject obj = flyings[i];  
            if (obj.shootBy(bullet)) { // 判断是否击中  
                index = i; // 记录被击中的飞行物的索引  
                break;  
            }  
        }  
        if (index != -1) { // 有击中的飞行物  
            FlyingObject one = flyings[index]; // 记录被击中的飞行物  
  
            FlyingObject temp = flyings[index]; // 被击中的飞行物与最后一个飞行物交换  
            flyings[index] = flyings[flyings.length - 1];  
            flyings[flyings.length - 1] = temp;  
  
            flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除最后一个飞行物(即被击中的)  
  
            // 检查one的类型(敌人加分，奖励获取)  
            if (one instanceof Enemy) { // 检查类型，是敌人，则加分  
                Enemy e = (Enemy) one; // 强制类型转换  
                score += e.getScore(); // 加分  
            } else { // 若为奖励，设置奖励  
                Award a = (Award) one;  
                int type = a.getType(); // 获取奖励类型  
                switch (type) {  
                case Award.DOUBLE_FIRE:  
                    hero.addDoubleFire(); // 设置双倍火力  
                    break;  
                case Award.LIFE:  
                    hero.addLife(); // 设置加命  
                    break;  
                }  
            }  
        }  
    }  
  
    /** 
     * 随机生成飞行物 
     *  
     * @return 飞行物对象 
     */  
    public static FlyingObject nextOne() {  
        Random random = new Random();  
        int type = random.nextInt(20); // [0,20)  
        if (type < 4) {  
            return new Bee();  
        } else {  
            return new Airplane();  
        }  
    }  
  
}  
```

#写在最后
以上就是这个游戏我整理的完整代码，因为图片差不多9张，所以图片没上传，需要图片的友友请简信我，最后，我做了一张思维导图贴出来让大家更好的理解OOP面向对象编程的过程。

![](http://wblearn.github.io/img/in-post/public/2556999-8217513e89c06a4a.webp)



>ps：码字很累，友友们点个赞或者关注，谢谢，么么哒~~
>资源已上传(包括图片)，下载地址请<a href="http://download.csdn.net/detail/wudalang_gd/9694422">戳这里</a>

------2016/12/25更新---------------

上面的小飞机游戏下载地址失效了，最近开通了[我的github博客](https://wblearn.github.io/)，欢迎大家测试，同时方便以后托管一些项目及资源。下载地址：[Java打飞机小游戏（附完整源码）](https://github.com/wblearn/Small-plane-game)