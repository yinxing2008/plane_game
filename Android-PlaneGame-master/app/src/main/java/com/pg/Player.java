package com.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @author Himi
 *
 */
public class Player {
	//���ǵ�Ѫ����Ѫ��λͼ
	//Ĭ��3Ѫ
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;
	//���ǵ������Լ�λͼ
	public int x, y,xtarget,ytarget,xmove,ymove;
	private Bitmap bmpPlayer;
	//�����ƶ��ٶ�
	private int speed = 5;
	//�����ƶ���ʶ�������½��ѽ��⣬�㶮�ã�
	public boolean isUp, isDown, isLeft, isRight,isTouch;
	//��ײ�����޵�ʱ��
	//��ʱ��
	private int noCollisionCount = 0;
	//��Ϊ�޵�ʱ��
	private int noCollisionTime = 40;
	//�Ƿ���ײ�ı�ʶλ
	private boolean isCollision;

	//���ǵĹ��캯��
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight()/2;
	}

	//���ǵĻ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//��������
		//�������޵�ʱ��ʱ����������˸
		if (isCollision) {
			//ÿ2����Ϸѭ��������һ������
			if (noCollisionCount % 2 == 0) {
				canvas.drawBitmap(bmpPlayer, x-bmpPlayer.getWidth()/2, y-bmpPlayer.getHeight()/2, paint);
			}
		} else {
			canvas.drawBitmap(bmpPlayer, x-bmpPlayer.getWidth()/2, y-bmpPlayer.getHeight()/2, paint);
		}
		//��������Ѫ��
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}

	//ʵ�尴��
	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = true;
		}
	}

	//ʵ�尴��̧��
	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = false;
		}
	}
	
	public void onTouchEvent(MotionEvent event){
			xtarget = (int)event.getX();
			ytarget = (int)event.getY();
			if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE	)
				{	isTouch = true;
					if(isTouch)
					{
						if(x < xtarget)
							x += speed+(xtarget-x)*0.1;
						else
							x -= speed+(x-xtarget)*0.1;
						if(y < ytarget)
							y += speed+(ytarget-y)*0.1;
						else 
							y -= speed+(y-ytarget)*0.1;
						isTouch = false;
					
					xtarget = x;
					ytarget = y;
					
					}
				}
	}
	
	
	
	//���ǵ��߼�
	public void logic() {
		//���������ƶ�
		if (isLeft) {
			x -= speed;
		}
		if (isRight) {
			x += speed;
		}
		if (isUp) {
			y -= speed;
		}
		if (isDown) {
			y += speed;
		}
		

		//�ж���ĻX�߽�
		if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
			x = MySurfaceView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		//�ж���ĻY�߽�
		if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
			y = MySurfaceView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}
		//�����޵�״̬
		if (isCollision) {
			//��ʱ����ʼ��ʱ
			noCollisionCount++;
			if (noCollisionCount >= noCollisionTime) {
				//�޵�ʱ����󣬽Ӵ��޵�״̬����ʼ��������
				isCollision = false;
				noCollisionCount = 0;
			}
		}
	}

	//��������Ѫ��
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}

	//��ȡ����Ѫ��
	public int getPlayerHp() {
		return playerHp;
	}

	//�ж���ײ(������л�)
	public boolean isCollsionWith(Enemy en) {
		//�Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = en.x;
			int y2 = en.y;
			int w2 = en.frameW;
			int h2 = en.frameH;
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			//��ײ�������޵�״̬
			isCollision = true;
			return true;
			//�����޵�״̬��������ײ
		} else {
			return false;
		}
	}
	//�ж���ײ(������л��ӵ�)
	public boolean isCollsionWith(Bullet bullet) {
		//�Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = bullet.bulletX;
			int y2 = bullet.bulletY;
			int w2 = bullet.bmpBullet.getWidth();
			int h2 = bullet.bmpBullet.getHeight();
			if (x+bmpPlayer.getWidth()/3 >= x2 && x+bmpPlayer.getWidth()/3 >= x2 + w2) {
				return false;
			} else if (x+bmpPlayer.getWidth()*2/3<= x2 && x + bmpPlayer.getWidth()*2/3 <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			//��ײ�������޵�״̬
			isCollision = true;
			return true;
			//�����޵�״̬��������ײ
		} else {
			return false;
		}
	}
}
