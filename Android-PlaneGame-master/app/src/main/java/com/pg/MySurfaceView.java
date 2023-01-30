package com.pg;


import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 
 * @author Himi
 *
 */

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Paint paint;
	private Paint paint2;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	public static int screenW, screenH;
	public static int Pause_flag = 0;
	
	//������Ϸ״̬����
	public static final int GAME_MENU = 0;//��Ϸ�˵�
	public static final int GAMEING = 1;//��Ϸ��
	public static final int GAME_WIN = 2;//��Ϸʤ��
	public static final int GAME_LOST = 3;//��Ϸʧ��
	public static final int GAME_PAUSE = -1;//��Ϸ�˵�
	
	//��ǰ��Ϸ״̬(Ĭ�ϳ�ʼ����Ϸ�˵�����)
	public static int gameState = GAME_MENU;
	//����һ��Resourcesʵ�����ڼ���ͼƬ
	private Resources res = this.getResources();
	//������Ϸ��Ҫ�õ���ͼƬ��Դ(ͼƬ����)
	private Bitmap bmpBackGround;//��Ϸ����
	private Bitmap bmpStart1;
	private Bitmap bmpStart2;
	private Bitmap bmpSound,bmpSound2;
	public static boolean soundFlag = true;
	
	private Bitmap bmpGamePause; //��Ϸ��ͣ
	private Bitmap bmpPause_bg;
	private Bitmap bmpPause_back;
	private Bitmap bmpPause_continue;
	private Bitmap bmpPause_exit;
	
	public static Bitmap bmpPause_canvas; 
	public static Bitmap bmpPause_canvas2;
	
	private Bitmap bmpBoom;//��ըЧ��
	private Bitmap bmpBoosBoom;//Boos��ըЧ��
	private Bitmap bmpButton;//��Ϸ��ʼ��ť
	private Bitmap bmpButtonPress;//��Ϸ��ʼ��ť�����
	private Bitmap bmpEnemyDuck;//����Ѽ��
	private Bitmap bmpEnemyFly;//�����Ӭ
	private Bitmap bmpEnemyBoss;//������ͷBoss
	
	private Bitmap bmpEnemyNew;
	private Bitmap bmpEnemyWeapon;
	
	private Bitmap bmpGameWin;//��Ϸʤ������
	private Bitmap bmpGameLost;//��Ϸʧ�ܱ���
	
	private Bitmap bmpPlayer;//��Ϸ���Ƿɻ�
	private Bitmap bmpPlayerHp;//���Ƿɻ�Ѫ��
	
	public static int playerWeaponLevel = 1; 
	
	private Bitmap bmpMenu;//�˵�����
	public static Bitmap bmpBullet,bmpBullet2;//�ӵ�
	public static Bitmap bmpEnemyBullet;//�л��ӵ�
	public static Bitmap bmpBossBullet;//Boss�ӵ�
	//����һ���˵�����
	private GameMenu gameMenu;
	//����һ��������Ϸ��������
	private GameBg backGround;
	
	private GamePause gamePause;
	
	public static SoundIcon soundIcon;
	
	//�������Ƕ���
	private Player player;
	//����һ���л�����
	private Vector<Enemy> vcEnemy;
	//ÿ�����ɵл���ʱ��(����)
	private int createEnemyTime = 50;
	private int count;//������
	
	
	//�������飺1��2��ʾ�л������࣬-1��ʾBoss
	//��ά�����ÿһά����һ�����
	
	private int enemyArray[][] = {{1,2,3,4},{2,1,4,2},{2,3,2,3},{1,1,1},{5,2} ,{ 2, 1 ,5}, { -1 } };
	//��ǰȡ��һά������±�
	public static int enemyArrayIndex;
	//�Ƿ����Boss��ʶλ
	private boolean isBoss;
	//����⣬Ϊ�����ĵл������漴����
	private Random random;
	//�л��ӵ�����
	private Vector<Bullet> vcBullet;
	//����ӵ��ļ�����
	private int countEnemyBullet;
	//�����ӵ�����
	private Vector<Bullet> vcBulletPlayer;
	//����ӵ��ļ�����
	private int countPlayerBullet;
	//��ըЧ������
	private Vector<Boom> vcBoom;
	//����Boss
	private Boss boss;
	//Boss���ӵ�����
	public static Vector<Bullet> vcBulletBoss;
	
	//��Ч ����
	public static SoundPool sp;
	//soundId_longΪ��ը������ shootΪ���ǿ������� enemy_shootΪ���˿�����
	public static int soundId_long,shoot,enemy_shoot;
	
	//��������
	
	public static MediaPlayer mediaPlayer;
	public static MediaPlayer mediaPlayer2;
	private AudioManager am;
	
	
	private Bitmap bmpclip;
	private  Canvas canvasclip;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint2 = new Paint();
		paint2.setColor(Color.WHITE);
		paint2.setAntiAlias(true);
			
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		sp = new SoundPool(4, AudioManager.STREAM_MUSIC,100);
		soundId_long = sp.load(context,R.raw.boom,1);
		shoot = sp.load(context, R.raw.shoot,1);
		enemy_shoot = sp.load(context, R.raw.enemy_shoot,1);
			
		//���ñ�������
		this.setKeepScreenOn(true);
	}

	/**
	 * SurfaceView��ͼ��������Ӧ�˺���
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		
		mediaPlayer = MediaPlayer.create(getContext(), R.raw.bg1);
		mediaPlayer2 = MediaPlayer.create(getContext(), R.raw.bg2);
		mediaPlayer.setLooping(true);
		mediaPlayer2.setLooping(true);
		
		bmpclip = Bitmap.createBitmap(this.getWidth(),this.getHeight(),Config.ARGB_8888);
		canvasclip = new Canvas(bmpclip);
		canvasclip.drawColor(Color.WHITE);
		
		am = (AudioManager)MainActivity.instance.getSystemService(Context.AUDIO_SERVICE);

		initGame();
		flag = true;
		//ʵ���߳�
		th = new Thread(this);
		//�����߳�
		th.start();
	}

	/*
	 * �Զ������Ϸ��ʼ������
	 */
void initGame() {
		//������Ϸ�����̨���½�����Ϸʱ����Ϸ������!
		//����Ϸ״̬���ڲ˵�ʱ���Ż�������Ϸ
		if (gameState == GAME_MENU) {
			//������Ϸ��Դ
			bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.bg3);
			bmpStart1 = BitmapFactory.decodeResource(res, R.drawable.start1);	
			bmpStart1 = BitmapFactory.decodeResource(res, R.drawable.start2);	
			
			bmpSound = BitmapFactory.decodeResource(res,R.drawable.sound);
			bmpSound2 = BitmapFactory.decodeResource(res,R.drawable.sound2);
						
			bmpGamePause = BitmapFactory.decodeResource(res, R.drawable.gamepause);
			bmpPause_bg = BitmapFactory.decodeResource(res, R.drawable.pause_bg);
			bmpPause_back = BitmapFactory.decodeResource(res, R.drawable.pause_back);
			bmpPause_continue = BitmapFactory.decodeResource(res, R.drawable.pause_continue);
			bmpPause_exit = BitmapFactory.decodeResource(res, R.drawable.pause_exit);
			
			bmpPause_canvas = Bitmap.createBitmap(screenW,screenH,Bitmap.Config.ARGB_8888);
			
			bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
			bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
			bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
			bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
			bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck2);
			bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly2);
			bmpEnemyBoss = BitmapFactory.decodeResource(res, R.drawable.enemy_boss);
			//����ӵĵ���
			bmpEnemyNew = BitmapFactory.decodeResource(res, R.drawable.enemy_new);
			bmpEnemyWeapon = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
			
			
			bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
			bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelost);
			bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
			bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
			bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
			bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
			bmpBullet2 = BitmapFactory.decodeResource(res, R.drawable.bullet2);
			
			bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
			bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);
			//��ըЧ������ʵ��
			vcBoom = new Vector<Boom>();
			//�л��ӵ�����ʵ��
			vcBullet = new Vector<Bullet>();
			//�����ӵ�����ʵ��
			vcBulletPlayer = new Vector<Bullet>();
			
			//�˵���ʵ��
			gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress,bmpStart1,bmpStart2);
			//��ͣ�˵�ʵ��
			gamePause = new GamePause(bmpPause_bg,bmpPause_back,bmpPause_continue,bmpPause_exit);
			//������ʵ��
			soundIcon = new SoundIcon(bmpSound, bmpSound2);
			
			//ʵ����Ϸ����
			backGround = new GameBg(bmpBackGround,bmpGamePause);
			//ʵ������
			player = new Player(bmpPlayer, bmpPlayerHp);
			//ʵ���л�����
			vcEnemy = new Vector<Enemy>();
			//ʵ�������
			random = new Random();
			//---Boss���
			//ʵ��boss����
			boss = new Boss(bmpEnemyBoss);
			//ʵ��Boss�ӵ�����
			vcBulletBoss = new Vector<Bullet>();
		}
	}

	/**
	 * ��Ϸ��ͼ
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null ) {
				canvas.drawColor(Color.WHITE);
				
				//��ͼ����������Ϸ״̬��ͬ���в�ͬ����
				switch (gameState) {
				case GAME_MENU:
					//�˵��Ļ�ͼ����
					if(Pause_flag == 1){
						initGame();
						//���ù������
						isBoss = false;
						playerWeaponLevel = 1;
						enemyArrayIndex = 0;
						Pause_flag = 0;
					}
					if(!soundFlag)
						mediaPlayer.start();
					
					gameMenu.draw(canvas, paint);					
					soundIcon.draw(canvas, paint);
					break;
				case GAMEING:
					//��Ϸ����������canvasclip��ʵʱ��ͼ��Ϊ����ͣ��������ı��������鱾283
					
					if(!soundFlag)
						mediaPlayer2.start();
					
					backGround.draw(canvas, paint);			
					backGround.draw(canvasclip, paint);
					
					//���ǻ�ͼ����
			
					player.draw(canvas, paint);
					player.draw(canvasclip, paint);
					
					if (isBoss == false) {
						//�л�����
						for (int i = 0; i < vcEnemy.size(); i++) {
							vcEnemy.elementAt(i).draw(canvas, paint);
							vcEnemy.elementAt(i).draw(canvasclip, paint);
						}
						//�л��ӵ�����
						for (int i = 0; i < vcBullet.size(); i++) {
							vcBullet.elementAt(i).draw(canvas, paint);
							vcBullet.elementAt(i).draw(canvasclip, paint);
						}
					} else {
						//Boos�Ļ���
					
						boss.draw(canvas, paint);
						boss.draw(canvasclip, paint);
						
						//Boss�ӵ��߼�
						for (int i = 0; i < vcBulletBoss.size(); i++) {
							vcBulletBoss.elementAt(i).draw(canvas, paint);
							vcBulletBoss.elementAt(i).draw(canvasclip, paint);
						}
					}
					//���������ӵ�����
					for (int i = 0; i < vcBulletPlayer.size(); i++) {
					
						vcBulletPlayer.elementAt(i).draw(canvas, paint);
						vcBulletPlayer.elementAt(i).draw(canvasclip, paint);
						
					}
					//��ըЧ������
					for (int i = 0; i < vcBoom.size(); i++) {
					
						vcBoom.elementAt(i).draw(canvas, paint);						
						vcBoom.elementAt(i).draw(canvasclip, paint);
						
					}		            
					
					break;
				case GAME_PAUSE:
					
					paint2.setAlpha(245);
					bmpPause_canvas2 = fastblur(bmpclip, 4);
					canvas.drawBitmap(bmpPause_canvas2, 0, 0,paint);
					
					gamePause.draw(canvas, paint2);
					
					soundIcon.draw(canvas, paint);
					break;
					
				case GAME_WIN:
					canvas.drawBitmap(bmpGameWin, 0, 0, paint);
					break;
				case GAME_LOST:
					canvas.drawBitmap(bmpGameLost, 0, 0, paint);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null ){
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	/**
	 * �����¼�����
	 */
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//���������¼�����������Ϸ״̬��ͬ���в�ͬ����
		switch (gameState) {
		case GAME_MENU:
			//�˵��Ĵ����¼�����
			gameMenu.onTouchEvent(event);
			soundIcon.onTouchEvent(event);
			break;
		case GAMEING:
			player.onTouchEvent(event);
			backGround.onTouchEvent(event);						
			break;
		case GAME_PAUSE:
			soundIcon.onTouchEvent(event);
			gamePause.onTouchEvent(event);
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:

			break;
		}
		return true;
	}

	/**
	 * ���������¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
				gameState = GAME_MENU;
				//Boss״̬����Ϊû����
				isBoss = false;
				//������Ϸ
				initGame();
				//���ù������
				enemyArrayIndex = 0;
			} else if (gameState == GAME_MENU) {
				//��ǰ��Ϸ״̬�ڲ˵����棬Ĭ�Ϸ��ذ����˳���Ϸ
				MainActivity.instance.finish();
				System.exit(0);
			}
			//��ʾ�˰����Ѵ������ٽ���ϵͳ����
			//�Ӷ�������Ϸ�������̨
			return true;
		}
		//���������¼�����������Ϸ״̬��ͬ���в�ͬ����
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			//���ǵİ��������¼�
			player.onKeyDown(keyCode, event);
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ����̧���¼�����
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
				gameState = GAME_MENU;
			}
			//��ʾ�˰����Ѵ������ٽ���ϵͳ����
			//�Ӷ�������Ϸ�������̨
			return true;
		}
		//���������¼�����������Ϸ״̬��ͬ���в�ͬ����
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			//����̧���¼�
			player.onKeyUp(keyCode, event);
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ��Ϸ�߼�
	 */
	private void logic() {
		//�߼����������Ϸ״̬��ͬ���в�ͬ����
		switch (gameState) {
		case GAME_MENU:		
			break;
		case GAMEING:
			//�����߼�
			backGround.logic();
			//�����߼�
			player.logic();
			//�л��߼�
			if (isBoss == false) {
				//�л��߼�
				for (int i = 0; i < vcEnemy.size(); i++) {
					Enemy en = vcEnemy.elementAt(i);
					//��Ϊ����������ӵл� ����ô�Եл�isDead�ж���
					//�����������ô�ʹ�������ɾ��,�����������Ż����ã�
					if (en.isDead) {
						vcEnemy.removeElementAt(i);
					} else {
						en.logic();
					}
				}
				//���ɵл�
				count++;
				if (count % createEnemyTime == 0) {
					for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
						//��Ӭ
						if (enemyArray[enemyArrayIndex][i] == 1) {
							int x = random.nextInt(screenW - 100) + 50;
							vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
							//Ѽ����
						} else if (enemyArray[enemyArrayIndex][i] == 2) {
							int y = random.nextInt(30);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));
							//Ѽ����
						} else if (enemyArray[enemyArrayIndex][i] == 3) {
							int y = random.nextInt(45);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW + 50, y));
						}else if (enemyArray[enemyArrayIndex][i] == 4) {
							int x = random.nextInt(screenW-50);
							vcEnemy.addElement(new Enemy(bmpEnemyNew, 4,x,-50));
						}else if (enemyArray[enemyArrayIndex][i] == 5) {
							int x = random.nextInt(screenW - 100) + 50;
							vcEnemy.addElement(new Enemy(bmpEnemyWeapon, 5, x, -50));
						}
					}
					//�����ж���һ���Ƿ�Ϊ���һ��(Boss)
					if (enemyArrayIndex == enemyArray.length - 1) {
						isBoss = true;
					} else {
						enemyArrayIndex++;
					}
				}
				//����л������ǵ���ײ
				for (int i = 0; i < vcEnemy.size(); i++) {
					if (player.isCollsionWith(vcEnemy.elementAt(i))) {
						//������ײ������Ѫ��-1
						if (vcEnemy.elementAt(i).type == 5){
							if(playerWeaponLevel<=3)
								playerWeaponLevel++;
						}
						else{
							player.setPlayerHp(player.getPlayerHp() - 1);
							if(playerWeaponLevel>1)
								playerWeaponLevel--;
							if(!soundFlag){
								sp.play(soundId_long, 1f, 1f, 0, 0, 1);
							}
						}
						
						//������Ѫ��С��0���ж���Ϸʧ��
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_LOST;
						}
					}
				}
				//ÿ1�����һ���л��ӵ�
				countEnemyBullet++;
				if (countEnemyBullet % 15 == 0) {
					for (int i = 0; i < vcEnemy.size(); i++) {
						Enemy en = vcEnemy.elementAt(i);
						//��ͬ���͵л���ͬ���ӵ����й켣
						int bulletType = 0;
						switch (en.type) {
						//��Ӭ
						case Enemy.TYPE_FLY:
							bulletType = Bullet.BULLET_FLY;
							vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
							if(!soundFlag){
								sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
							}
							break;
						//Ѽ��
						case Enemy.TYPE_DUCKL:
							bulletType = Bullet.BULLET_DUCK;
							vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
							if(!soundFlag){
								sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
							}
							break;
						case Enemy.TYPE_DUCKR:
							bulletType = Bullet.BULLET_DUCK;
							vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
							if(!soundFlag){
								sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
							}
							break;
						case Enemy.TYPE_NEW:
							bulletType = Bullet.BULLET_BOSS;
							vcBullet.add(new Bullet(bmpBossBullet, en.x + 10, en.y + 20, bulletType,Bullet.DIR_DOWN_LEFT));
							if(!soundFlag){
								sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
							}
							vcBullet.add(new Bullet(bmpBossBullet, en.x + 10, en.y + 20, bulletType,Bullet.DIR_DOWN));
							if(!soundFlag){
								sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
							}
							vcBullet.add(new Bullet(bmpBossBullet, en.x + 10, en.y + 20, bulletType,Bullet.DIR_DOWN_RIGHT));
							if(!soundFlag){
								sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
							}		
							break;
						case Enemy.TYPE_WEAPON:
							break;
						}
						
					}
				}
				//����л��ӵ��߼�
				for (int i = 0; i < vcBullet.size(); i++) {
					Bullet b = vcBullet.elementAt(i);
					if (b.isDead) {
						vcBullet.removeElement(b);
					} else {
						b.logic();
					}
				}
				//����л��ӵ���������ײ
				for (int i = 0; i < vcBullet.size(); i++) {
					if (player.isCollsionWith(vcBullet.elementAt(i))) {
						//������ײ������Ѫ��-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						if(playerWeaponLevel>1)
							playerWeaponLevel--;
						//������Ѫ��С��0���ж���Ϸʧ��
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_LOST;
						}
					}
				}
				//���������ӵ���л���ײ
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					//ȡ�������ӵ�������ÿ��Ԫ��
					Bullet blPlayer = vcBulletPlayer.elementAt(i);
					for (int j = 0; j < vcEnemy.size(); j++) {
						//��ӱ�ըЧ��
						//ȡ���л�������ÿ��Ԫ�������ӵ������ж�
						if (vcEnemy.elementAt(j).isCollsionWith(blPlayer)) {
							vcBoom.add(new Boom(bmpBoom, vcEnemy.elementAt(j).x, vcEnemy.elementAt(j).y, 7));
							if(!soundFlag){
								sp.play(soundId_long, 1f, 1f, 0, 0, 1);
							}
						}
					}
				}
			} else {//Boss����߼�
				//ÿ0.25�����һ�������ӵ�
				boss.logic();
				if (boss.hp>=67&&countPlayerBullet % 13 == 0) {
					//Boss��û����֮ǰ����ͨ�ӵ�
					vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x+50, boss.y+30, Bullet.BULLET_FLY));
					if(!soundFlag){
						sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
					}
				}
				else if(67>boss.hp&&boss.hp>=34&&countPlayerBullet % 8 == 0){
						vcBulletBoss.add(new Bullet(bmpEnemyBullet, boss.x+50, boss.y+30, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_Random));			
						if(!MySurfaceView.soundFlag)
							MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
				}
				else if(34>boss.hp&&countPlayerBullet % 13 == 0){			
					vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x+50, boss.y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
					vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x+50, boss.y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
					vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x+50, boss.y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
					if(!MySurfaceView.soundFlag)
						MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
			}
				if(countPlayerBullet % 100 == 0)
					Bullet.flag = -Bullet.flag;
					
				
				//Boss�ӵ��߼�
				for (int i = 0; i < vcBulletBoss.size(); i++) {
					Bullet b = vcBulletBoss.elementAt(i);
					if (b.isDead) {
						vcBulletBoss.removeElement(b);
					} else {
						b.logic();
					}
				}
				//Boss�ӵ������ǵ���ײ
				for (int i = 0; i < vcBulletBoss.size(); i++) {
					if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
						//������ײ������Ѫ��-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						if(playerWeaponLevel>1)
							playerWeaponLevel--;
						//������Ѫ��С��0���ж���Ϸʧ��
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_LOST;
						}
					}
				}
				//Boss�������ӵ����У�������ըЧ��
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					Bullet b = vcBulletPlayer.elementAt(i);
					if (boss.isCollsionWith(b)) {
						if(!soundFlag){
							sp.play(soundId_long, 1f, 1f, 0, 0, 1);
						}
						if (boss.hp <= 0) {
							//��Ϸʤ��
							gameState = GAME_WIN;
						} else {
							//��ʱɾ��������ײ���ӵ�����ֹ�ظ��ж����ӵ���Boss��ײ��
							b.isDead = true;
							//BossѪ����1
							boss.setHp(boss.hp - 1);
							//��Boss���������Boss��ըЧ��
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 15, boss.y + 25, 5));
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35, boss.y + 40, 5));
							vcBoom.add(new Boom(bmpBoosBoom, boss.x + 65, boss.y + 20, 5));
						}
					}
				}
			}
			//ÿ0.5�����һ�������ӵ�
			countPlayerBullet++;
			if (countPlayerBullet % 10 == 0) {
				if(playerWeaponLevel == 1)
					vcBulletPlayer.add(new Bullet(bmpBullet, player.x - bmpPlayer.getWidth()/5, player.y -15 , Bullet.BULLET_PLAYER));
				else if(playerWeaponLevel == 2){
					vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth()/5, player.y -15, Bullet.BULLET_PLAYER,Bullet.DIR_UP_LEFT2));
					vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth()/5, player.y -15, Bullet.BULLET_PLAYER,Bullet.DIR_UP_RIGHT2));
				}else if(playerWeaponLevel == 3){
					vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth()/5, player.y -15, Bullet.BULLET_PLAYER,Bullet.DIR_UP_LEFT2));
					vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth()/5, player.y -15, Bullet.BULLET_PLAYER));
					vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth()/5, player.y -15, Bullet.BULLET_PLAYER,Bullet.DIR_UP_RIGHT2));
				}
				if(!soundFlag){
					sp.setVolume(shoot, 0.5f, 0.5f);
					sp.play(shoot, 0.8f, 0.8f, 0, 0, 1);
				}
			}
			//���������ӵ��߼�
			for (int i = 0; i < vcBulletPlayer.size(); i++) {
				Bullet b = vcBulletPlayer.elementAt(i);
				if (b.isDead) {
					vcBulletPlayer.removeElement(b);
				} else {
					b.logic();
				}
			}
			//��ըЧ���߼�
			for (int i = 0; i < vcBoom.size(); i++) {
				Boom boom = vcBoom.elementAt(i);
				if (boom.playEnd) {
					//������ϵĴ�������ɾ��
					vcBoom.removeElementAt(i);
				} else {
					vcBoom.elementAt(i).logic();
				}
			}
			break;
		case GAME_PAUSE:
			
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//����ģ���������ٶȻ����ģ�ԭ����
	private Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }
        yw = yi = 0;
        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;
        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

	/**
	 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/**
	 * SurfaceView��ͼ����ʱ����Ӧ�˺���
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
}
