/**
 * 
 */
package com.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Boss
 * @author Himi
 *
 */
public class Boss {
	//Boss��Ѫ��
	public int hp = 100;
	//Boss��ͼƬ��Դ
	private Bitmap bmpBoss;
	//Boss����
	public int x, y;
	//Bossÿ֡�Ŀ��
	public int frameW, frameH;
	//Boss��ǰ֡�±�
	private int frameIndex;
	//Boss�˶����ٶ�
	private int speed = 6;
	//Boss���˶��켣
	//һ��ʱ���������Ļ�·��˶������ҷ����Χ�ӵ������Ƿ��̬��
	//����״̬�� ���ӵ���ֱ�����˶�
	private boolean isCrazy;
	//������״̬��״̬ʱ����
	private int crazyTime = 150;
	
	private int crazyTime2 = 50;
	//������
	private int count;

	//Boss�Ĺ��캯��
	public Boss(Bitmap bmpBoss) {
		this.bmpBoss = bmpBoss;
		frameW = bmpBoss.getWidth() / 10;
		frameH = bmpBoss.getHeight();
		//Boss��X�������
		x = MySurfaceView.screenW / 2 - frameW / 2;
		y = 0;
	}

	//Boss�Ļ���
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(bmpBoss, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	//Boss���߼�
	public void logic() {
		//����ѭ������֡�γɶ���
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		//û�з���״̬
		if (isCrazy == false) {
			if(hp>=34&&hp<67){
				if(x+frameW/2>MySurfaceView.screenW/2)
					x --;
				else 
					x ++;
			}
			else {
				x += speed;
				if (x + frameW*3/4 >= MySurfaceView.screenW) {
					speed = -speed;
				} else if (x+frameW/4 <= 0) {
					speed = -speed;
				}
			}
			count++;
			
			if (hp<34&&count % crazyTime == 0) {
				isCrazy = true;
				speed = 24;
				
			}
			//����״̬
		} else {
			speed -= 1;
			//��Boss����ʱ���������ӵ�
			if (speed == 0) {
				//���8�����ӵ�
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_LEFT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_RIGHT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_LEFT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_RIGHT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
				if(!MySurfaceView.soundFlag){
					MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
					MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 0.5f, 1f, 0, 0, 1);
					MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);					
				}
				
			}
			y += speed+3;
			if (y <= 0) {
				//�ָ�����״̬
				isCrazy = false;
				speed = 5;
			}
		}
	}

	//�ж���ײ(Boss�������ӵ�����)
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
		return true;
	}

	//����BossѪ��
	public void setHp(int hp) {
		this.hp = hp;
	}
}
