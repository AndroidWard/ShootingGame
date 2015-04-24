package com.example.shootinggame;

import java.io.InputStream;
import java.util.Random;

import com.example.shootinggame.R.drawable;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class AnimView extends SurfaceView implements Callback, Runnable {
	// 屏幕的宽高
	private int ScreenWidth = 0;
	private int ScreenHeight = 0;
	private int BgHeight = 0;
	private static final int STATE_GAME = 0;
	private int STATE = STATE_GAME;
	Paint paint = null;
	private Bitmap BACKGROUND = null;
	private int BACKGROUND_POS_Y0 = 0;
	private int BACKGROUND_POS_Y1 = 0;
	static final int PLAN_ANIM_COUNT = 6;
	// static final int BULLET_ANIM_COUNT = 4;
	static final int BULLET_ANIM_COUNT = 6;//
	static final int BULLET_POOL_COUNT = 15;
	// static final int PLAN_STEP = 10;
	static int PLAN_STEP = 30;
	static final int PLAN_INTERVAL = 500;
	static final int BULLET_UP_OFFSET = 40;
	static final int BULLET_LEFT_OFFSET = 5;
	static final int ENEMY_POOL_COUNT = 50;
	static final int ENEMY_ALIVE_COUNT = 1;
	static final int ENEMY_DEATH_COUNT = 6;
	static final int ENEMY_POS_OFF = 65;
	private Thread thread = null;
	private boolean isRunning = false;
	SurfaceHolder surfaceHolder = null;
	Canvas canvas = null;
	Context context = null;
	private Anim PLAN_ANIME = null;
	private int PLAN_POS_X = 0;
	private int PLAN_POS_Y = 0;
	private Enemy[] enemy = null;
	private Bullet[] bullet = null;
	private long sendTime = 0L;
	private boolean touching = false;
	private int TOUCH_POS_X = 0;
	private int TOUCH_POS_Y = 0;
	private int sendID = 0;
	private Bitmap[] bitBullet = null;

	public AnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public AnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public AnimView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AnimView(Context context, int screenWidth, int screenHeight) {
		super(context);
		this.context = context;
		paint = new Paint();
		ScreenWidth = screenWidth;
		ScreenHeight = screenHeight;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		setFocusable(true);
		init();
     
        
		setGameState(STATE_GAME);

		// TODO Auto-generated constructor stub
	}

	private void setGameState(int stateGame) {
		// TODO Auto-generated method stub
		STATE = stateGame;
	}

	private void init() {
		// TODO Auto-generated method stub
		BACKGROUND = ReadBitmap(context, R.drawable.map);
		PLAN_ANIME = new Anim(context, new int[] { R.drawable.plan_0,
				R.drawable.plan_1, R.drawable.plan_2, R.drawable.plan_3,
				R.drawable.plan_4, R.drawable.plan_5 }, true);
		BACKGROUND_POS_Y0 = 0;
		BACKGROUND_POS_Y1 = -BACKGROUND.getHeight();
		BgHeight = BACKGROUND.getHeight();

		PLAN_POS_X = 150;
		PLAN_POS_Y = 400;
		Bitmap[] ENEMY_MOVE = new Bitmap[ENEMY_ALIVE_COUNT];
		ENEMY_MOVE[0] = ReadBitmap(context, R.drawable.enemy);

		Bitmap[] ENEMY_DEAD = new Bitmap[ENEMY_DEATH_COUNT];
		for (int i = 0; i < ENEMY_DEATH_COUNT; i++) {
			ENEMY_DEAD[i] = ReadBitmap(context, R.drawable.bomb_enemy_0 + i);
		}
		enemy = new Enemy[ENEMY_POOL_COUNT];
		for (int i = 0; i < ENEMY_POOL_COUNT; i++) {
			enemy[i] = new Enemy(context, ENEMY_MOVE, ENEMY_DEAD);
			enemy[i].init(i * ENEMY_POS_OFF, 0);
		}
		bullet = new Bullet[BULLET_POOL_COUNT];
		bitBullet = new Bitmap[BULLET_ANIM_COUNT];
		for (int i = 0; i < BULLET_ANIM_COUNT; i++) {
			bitBullet[i] = ReadBitmap(context, R.drawable.bullet);
		}
		for (int i = 0; i < BULLET_POOL_COUNT; i++) {
			bullet[i] = new Bullet(context, bitBullet);
		}

		sendTime = System.currentTimeMillis();
	}

	/**
	 * 读取图片资源
	 */
	public Bitmap ReadBitmap(Context context, int resID) {
		BitmapFactory.Options opt = new BitmapFactory.Options();

		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;

		InputStream is = context.getResources().openRawResource(resID);
		return BitmapFactory.decodeStream(is, null, opt);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRunning) {
			synchronized (surfaceHolder) {
				canvas = surfaceHolder.lockCanvas();
				Draw();
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
			try {
				// Thread.sleep(100);
				Thread.sleep(20);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void Draw() {
		// TODO Auto-generated method stub
		switch (STATE) {
		case STATE_GAME:
			renderBg();
			updateBg();
			break;

		}
	}

	private void updateBg() {
		// TODO Auto-generated method stub
		// BACKGROUND_POS_Y0 += 10;
		// BACKGROUND_POS_Y1 += 10;
		BACKGROUND_POS_Y0 += 2;
		BACKGROUND_POS_Y1 += 2;
		if (BACKGROUND_POS_Y0 == BgHeight) {
			BACKGROUND_POS_Y0 = -BgHeight;
		}
		if (BACKGROUND_POS_Y1 == BgHeight) {
			BACKGROUND_POS_Y1 = -BgHeight;
		}
		if (touching) {
			if (PLAN_POS_X < TOUCH_POS_X) {
				PLAN_POS_X += PLAN_STEP;
			} else {
				PLAN_POS_X -= PLAN_STEP;
			}
			if (PLAN_POS_Y < TOUCH_POS_Y) {
				PLAN_POS_Y += PLAN_STEP;
			} else {
				PLAN_POS_Y -= PLAN_STEP;
			}
			if (Math.abs(PLAN_POS_X - TOUCH_POS_X) <= PLAN_STEP) {
				PLAN_POS_X = TOUCH_POS_X;
			}
			if (Math.abs(PLAN_POS_Y - TOUCH_POS_Y) <= PLAN_STEP) {
				PLAN_POS_Y = TOUCH_POS_Y;
			}
		}
		for (int i = 0; i < BULLET_POOL_COUNT; i++) {
			bullet[i].UpdateBullet();
		}
		for (int i = 0; i < ENEMY_POOL_COUNT; i++) {
			enemy[i].updateEnemy();
			if (enemy[i].ENEMY_STATE = Enemy.ENEMY_STATE_DEAD
					|| enemy[i].ENEMY_POS_Y >= ScreenHeight) {
				enemy[i].init(UtilRandom(0, ENEMY_POOL_COUNT) * ENEMY_POS_OFF,
						0);
			}
		}
		if (sendID < BULLET_POOL_COUNT) {
			long now = System.currentTimeMillis();
			if (now - sendTime >= PLAN_INTERVAL) {
				bullet[sendID].init(PLAN_POS_X - BULLET_LEFT_OFFSET, PLAN_POS_Y
						- BULLET_UP_OFFSET);
				sendTime = now;
				sendID++;
			} else {
				sendID = 0;
			}
			Collision();

		}

	}

	private void Collision() {
		// TODO Auto-generated method stub
		for (int i = 0; i < BULLET_POOL_COUNT; i++) {
			for (int j = 0; j < ENEMY_POOL_COUNT; j++) {
				if (enemy[j].ENEMY_POS_X <= bullet[i].BULLET_X
						&& (bullet[i].BULLET_X <= enemy[j].ENEMY_POS_X + 55)
						&& enemy[j].ENEMY_POS_Y <= bullet[i].BULLET_Y
						&& ((bullet[i].BULLET_Y <= enemy[j].ENEMY_POS_Y + 55))) {
					enemy[j].ANIMATION_STATE = Enemy.ENEMY_STATE_DEAD;
				}
			}
		}
	}

	private int UtilRandom(int button, int top) {
		// TODO Auto-generated method stub\
		return ((Math.abs(new Random().nextInt()) % (top - button)) + button);
	}

	private void renderBg() {
		// TODO Auto-generated method stub
		canvas.drawBitmap(BACKGROUND, 0, BACKGROUND_POS_Y0, paint);
		canvas.drawBitmap(BACKGROUND, 0, BACKGROUND_POS_Y1, paint);
		PLAN_ANIME.DrawAnimation(canvas, paint, PLAN_POS_X, PLAN_POS_Y);
		for (int i = 0; i < BULLET_POOL_COUNT; i++) {
			bullet[i].DrawBullet(canvas, paint);
		}
		for (int i = 0; i < ENEMY_POOL_COUNT; i++) {
			enemy[i].DrawEnemy(canvas, paint);
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isRunning = false;

	}

	public void UpdateTouchEvent(int x, int y, boolean touching) {
		switch (STATE) {
		case STATE_GAME:
			this.touching = touching;
			TOUCH_POS_X = x;
			TOUCH_POS_Y = y;
			break;

		}
	}

}
