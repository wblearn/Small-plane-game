package com.tarena.fly;

import java.awt.image.BufferedImage;

/**
 * 飞行物(敌机，蜜蜂，子弹，英雄机)
 */
public abstract class FlyingObject {
	protected int x;    //x坐标
	protected int y;    //y坐标
	protected int width;    //宽
	protected int height;   //高
	protected BufferedImage image;   //图片

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * 检查是否出界
	 * @return true 出界与否
	 */
	public abstract boolean outOfBounds();
	
	/**
	 * 飞行物移动一步
	 */
	public abstract void step();
	
	/**
	 * 检查当前飞行物体是否被子弹(x,y)击(shoot)中
	 * @param Bullet 子弹对象
	 * @return true表示被击中了
	 */
	public boolean shootBy(Bullet bullet){
		int x = bullet.x;  //子弹横坐标
		int y = bullet.y;  //子弹纵坐标
		return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
	}

}
