package com.pg;

import android.R.bool;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author Himi
 *
 */
public class GamePause {
	//�˵�����ͼ
	private Bitmap bmpPause_bg;
	//��ťͼƬ��Դ(���º�δ����ͼ)
	private Bitmap bmpButton, bmpButtonPress,bmpPause_continue,bmpPause_exit;
	//��ť������
	private int btnX, btnY,btn_continue_x,btn_continue_y,btn_exit_x,btn_exit_y;
	//��ť�Ƿ��±�ʶλ
	private Boolean isPress,flag;
	//�˵���ʼ��
	public GamePause(Bitmap bmpPause_bg,Bitmap bmpPause_back,Bitmap bmpPause_continue,Bitmap bmpPause_exit) {
		this.bmpPause_bg = bmpPause_bg;
		this.bmpButton = bmpPause_back;
		this.bmpPause_continue = bmpPause_continue;
		this.bmpPause_exit = bmpPause_exit;
		
		btn_continue_x = MySurfaceView.screenW / 2 - bmpPause_continue.getWidth() / 2;
		btn_continue_y = MySurfaceView.screenH *2/5- bmpPause_continue.getHeight();
		
		btnX = MySurfaceView.screenW / 2 - bmpButton.getWidth() / 2;
		btnY = MySurfaceView.screenH *4/6- bmpButton.getHeight();

		
		btn_exit_x = MySurfaceView.screenW / 2 - bmpPause_exit.getWidth() / 2;
		btn_exit_y = MySurfaceView.screenH*5/6- bmpPause_exit.getHeight();
	
		isPress = false;
	}
	//�˵���ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//���Ʋ˵�����ͼ
		canvas.drawBitmap(bmpPause_bg, 0, 0, paint);
		//����δ���°�ťͼ
		if (isPress) {//�����Ƿ��»��Ʋ�ͬ״̬�İ�ťͼ
			canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
		} else {
			canvas.drawBitmap(bmpButton, btnX, btnY, paint);
		}
		canvas.drawBitmap(bmpPause_continue, btn_continue_x,btn_continue_y, paint);
		canvas.drawBitmap(bmpPause_exit, btn_exit_x,btn_exit_y, paint);
	}
	//�˵������¼���������Ҫ���ڴ���ť�¼�
	public void onTouchEvent(MotionEvent event) {
		//��ȡ�û���ǰ����λ��
		int pointX = (int) event.getX();
		int pointY = (int) event.getY();
		//���û��ǰ��¶������ƶ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
				if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
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
			if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
				if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress = false;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					if(MySurfaceView.soundFlag){
						MySurfaceView.mediaPlayer.pause();
						MySurfaceView.mediaPlayer2.pause();
					} else {
						MySurfaceView.mediaPlayer2.pause();
						MySurfaceView.mediaPlayer.start();
					}		
					MySurfaceView.Pause_flag = 1;				
					MySurfaceView.gameState = MySurfaceView.GAME_MENU;
				}
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			if (pointX > btn_exit_x && pointX < btn_exit_x + bmpPause_exit.getWidth()) {
				if (pointY > btn_exit_y && pointY < btn_exit_y + bmpPause_exit.getHeight()) {
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
			if (pointX > btn_exit_x && pointX < btn_exit_x + bmpPause_exit.getWidth()) {
				if (pointY > btn_exit_y && pointY < btn_exit_y + bmpPause_exit.getHeight())  {
					//��ԭButton״̬Ϊδ����״̬
					isPress = false;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					MainActivity.instance.finish();
					System.exit(0);                           //�˳���Ϸ
					
				}
			}   
		}
		
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			//�ж��û��Ƿ����˰�ť
			if (pointX > btn_continue_x && pointX < btn_continue_x + bmpPause_continue.getWidth()) {
				if (pointY > btn_continue_y && pointY < btn_continue_y + bmpPause_continue.getHeight()) {
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
			if (pointX > btn_continue_x && pointX < btn_continue_x + bmpPause_continue.getWidth()) {
				if (pointY > btn_continue_y && pointY < btn_continue_y + bmpPause_continue.getHeight()) {
					//��ԭButton״̬Ϊδ����״̬
					isPress = false;
					//�ı䵱ǰ��Ϸ״̬Ϊ��ʼ��Ϸ
					MySurfaceView.gameState = MySurfaceView.GAMEING;
				}
			}   
		}			
	}	
}
