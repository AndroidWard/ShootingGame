package com.example.shootinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	static int BULLET_STEP_X = 3;
	static int BULLET_STEP_Y = 15;
	static int BULLET_WIDTH = 40;
	public int BULLET_X = 0;
	public int BULLET_Y = 0;
	public Anim BULLET_ANIM = null;
	boolean BULLET_FOCUS = false;
	Context context=null;

	public Bullet(Context context, Bitmap[] frameBitmap) {
		// TODO Auto-generated constructor stub
		this.context = context;
		BULLET_ANIM = new Anim(context, frameBitmap, true);
	}

	public void init(int x, int y) {

		BULLET_X = x;
		BULLET_Y = y;
		BULLET_FOCUS = true;

	}
    public void DrawBullet(Canvas canvas,Paint paint) {
		if (BULLET_FOCUS) {
			BULLET_ANIM.DrawAnimation(canvas, paint, BULLET_X,BULLET_Y);
			
		}
	}
      public void UpdateBullet() {
		if (BULLET_FOCUS) {
			BULLET_Y-=BULLET_STEP_Y;
		}
	}
      
}
