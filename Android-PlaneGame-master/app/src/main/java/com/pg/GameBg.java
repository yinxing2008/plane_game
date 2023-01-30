package com.pg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author Himi
 *
 */
public class GameBg {
	//��Ϸ������ͼƬ��Դ
	//Ϊ��ѭ�����ţ����ﶨ������λͼ����
	//����Դ���õ���ͬһ��ͼƬ
	private Bitmap bmpBackGround1;
	private Bitmap bmpBackGround2;
	private Bitmap bmpGamePause;
	//��Ϸ��������
	private int bg1x, bg1y, bg2x, bg2y;
	private int btn_x,btn_y;
	
	private boolean isPress;
	
	//���������ٶ�
	private int speed = 3;

	//��Ϸ�������캯��
	public GameBg(Bitmap bmpBackGround,Bitmap bmpGamePause) {
		this.bmpBackGround1 = bmpBackGround;
		this.bmpBackGround2 = bmpBackGround;
		this.bmpGamePause = bmpGamePause;
		//�����õ�һ�ű����ײ���������������Ļ
		bg1y = -Math.abs(bmpBackGround1.getHeight() - MySurfaceView.screenH);
		//�ڶ��ű���ͼ�����ڵ�һ�ű������Ϸ�
		//+101��ԭ����Ȼ���ű���ͼ�޷�϶���ӵ�����ΪͼƬ��Դͷβ
		//ֱ�����Ӳ���г��Ϊ�����Ӿ�������������ͼ���Ӷ�������λ��
		bg2y = bg1y - bmpBackGround1.getHeight() ;
		
		btn_x = (int)(MySurfaceView.screenW*0.9);
		btn_y = (int)(MySurfaceView.screenH*0.01);
		
	}
	//��Ϸ�����Ļ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//�������ű���
		canvas.drawBitmap(bmpBackGround1, bg1x, bg1y, paint);
		canvas.drawBitmap(bmpBackGround2, bg2x, bg2y, paint);
		canvas.drawBitmap(bmpGamePause,btn_x,btn_y,paint);
		
	}
	
	public void onTouchEvent(MotionEvent event) {
		//��ȡ�û���ǰ����λ��
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			if (pointX > btn_x && pointX < btn_x + bmpGamePause.getWidth()) {
				if (pointY > btn_y && pointY < btn_y + bmpGamePause.getHeight()) {
					isPress = true;
				} else {
					isPress = false;
				}
			} else {
				isPress = false;
			}
			//���û���̧����
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//̧���ж��Ƿ�����ť����ֹ�û��ƶ�����
			if (pointX > btn_x && pointX < btn_x + bmpGamePause.getWidth()) {
				if (pointY > btn_y && pointY < btn_y + bmpGamePause.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress = false;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					MySurfaceView.gameState = MySurfaceView.GAME_PAUSE;
				}
			}
		}
	}
	
	
	
	
	//��Ϸ�������߼�����
	public void logic() {
		bg1y += speed;
		bg2y += speed;
		//����һ��ͼƬ��Y���곬����Ļ��
		//���������������õ��ڶ���ͼ���Ϸ�
		if (bg1y > MySurfaceView.screenH) {
			bg1y = bg2y - bmpBackGround1.getHeight();
		}
		//���ڶ���ͼƬ��Y���곬����Ļ��
		//���������������õ���һ��ͼ���Ϸ�
		if (bg2y > MySurfaceView.screenH) {
			bg2y = bg1y - bmpBackGround1.getHeight() ;
		}
	}
}
