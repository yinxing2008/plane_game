package com.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Himi
 *
 */
public class Enemy {
	//�л��������ʶ
	public int type;
	//��Ӭ
	public static final int TYPE_FLY = 1;
	//Ѽ��(���������˶�)
	public static final int TYPE_DUCKL = 2;
	//Ѽ��(���������˶�)
	public static final int TYPE_DUCKR = 3;
	
	public static final int TYPE_NEW = 4;
	
	public static final int TYPE_WEAPON = 5;
	//�л�ͼƬ��Դ
	public Bitmap bmpEnemy;
	//�л�����
	public int x, y;
	//�л�ÿ֡�Ŀ��
	public int frameW, frameH;
	//�л���ǰ֡�±�
	private int frameIndex;
	//�л����ƶ��ٶ�
	private int speed,speed2;
	//�жϵл��Ƿ��Ѿ�����
	public boolean isDead;

	//�л��Ĺ��캯��
	public Enemy(Bitmap bmpEnemy, int enemyType, int x, int y) {
		this.bmpEnemy = bmpEnemy;
		frameW = bmpEnemy.getWidth() / 10;
		frameH = bmpEnemy.getHeight();
		this.type = enemyType;
		this.x = x;
		this.y = y;
		//��ͬ����ĵл�Ѫ����ͬ
		switch (type) {
		//��Ӭ
		case TYPE_FLY:
			speed2 = 5;
			speed = 5;
			break;
		//Ѽ��
		case TYPE_DUCKL:
			speed = 3;
			break;
		case TYPE_DUCKR:
			speed = 3;
			break;
			
		case TYPE_NEW:
			speed = 3;
			break;
		case TYPE_WEAPON:
			speed = 3;
			break;
		}
	}

	//�л���ͼ����
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(bmpEnemy, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	//�л��߼�AI
	public void logic() {
		//����ѭ������֡�γɶ���
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		//��ͬ����ĵл�ӵ�в�ͬ��AI�߼�
		switch (type) {
		case TYPE_FLY:
			if (isDead == false) {	
				x += speed2;
				if (x + frameW >= MySurfaceView.screenW) {
					speed2 = -speed2;
				} else if (x <= 0) {
					speed2 = -speed2;
				}
								
				y += speed;
				
				if (y >= MySurfaceView.screenH) {
					isDead = true;
				}
				
			}
			break;
		case TYPE_DUCKL:
			if (isDead == false) {
				//б���½��˶�
				x += speed+2;
				y += speed+5;
				if (x > MySurfaceView.screenW) {
					isDead = true;
				}
			}
			break;
		case TYPE_DUCKR:
			if (isDead == false) {
				//б���½��˶�
				x -= speed+3;
				y += speed+5;
				if (x < -50) {
					isDead = true;
				}
			}
			break;
		case TYPE_NEW:
			if (isDead == false) {
				//б���½��˶�
				y += speed*2;
 
				if (y > MySurfaceView.screenH) {
					isDead = true;
				}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
			}
			break;
		case TYPE_WEAPON:
			if (isDead == false) {	
				x += speed2;
				if (x + frameW >= MySurfaceView.screenW) {
					speed2 = -speed2;
				} else if (x <= 0) {
					speed2 = -speed2;
				}	
				y += speed;				
				if (y >= MySurfaceView.screenH) {
					isDead = true;
				}
				
			}
			break;
		}
	}

	//�ж���ײ(�л��������ӵ���ײ)
	public boolean isCollsionWith(Bullet bullet) {
		int x2 = bullet.bulletX;
		int y2 = bullet.bulletY;
		int w2 = bullet.bmpBullet.getWidth();
		int h2 = bullet.bmpBullet.getHeight();
		if (x >= x2 && x >= x2 + w2) {
			return false;
		} else if (x <= x2 && x + frameW <= x2) {
			return false;
		} else if (y >= y2 && y >= y2 + h2) {
			return false;
		} else if (y <= y2 && y + frameH <= y2) {
			return false;
		}
		//������ײ����������
		isDead = true;
		return true;
	}
}
