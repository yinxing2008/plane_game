package com.pg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;


/**
 * @author Himi
 *
 */
public class GameMenu {
	//�˵�����ͼ
	private Bitmap bmpMenu;
	//��ťͼƬ��Դ(���º�δ����ͼ)
	private Bitmap bmpButton, bmpButtonPress,bmpstart1,bmpstart2;
	//��ť������
	private int btnX, btnY;
	//��ť�Ƿ��±�ʶλ
	private Boolean isPress;
	
	//�˵���ʼ��
	public GameMenu(Bitmap bmpMenu, Bitmap bmpButton, Bitmap bmpButtonPress,Bitmap bmpStart1,Bitmap bmpStart2) {
		this.bmpMenu = bmpMenu;
		this.bmpButton = bmpButton;
		this.bmpButtonPress = bmpButtonPress;
		this.bmpstart1 = bmpStart1;
		this.bmpstart2 = bmpStart2;
		//X���У�Y������Ļ�ײ�
		btnX = MySurfaceView.screenW / 2 - bmpButton.getWidth() / 2;
		btnY = MySurfaceView.screenH - bmpButton.getHeight();
		
		isPress = false;
	}
	//�˵���ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//���Ʋ˵�����ͼ
		canvas.drawBitmap(bmpMenu, 0, 0, paint);
		//����δ���°�ťͼ
			canvas.drawBitmap(bmpstart1, -35, MySurfaceView.screenH/9,paint);
		if (isPress) {//�����Ƿ��»��Ʋ�ͬ״̬�İ�ťͼ
			canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
		} else {
			canvas.drawBitmap(bmpButton, btnX, btnY, paint);
		}
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
						//MySurfaceView.mediaPlayer2.start();
					} else {
						MySurfaceView.mediaPlayer.pause();
						MySurfaceView.mediaPlayer2.start();
					}
					MySurfaceView.gameState = MySurfaceView.GAMEING;
				}
			}
		}
	}
	
}
