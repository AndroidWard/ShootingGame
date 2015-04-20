package com.example.shootinggame;

import java.io.InputStream;




import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Anim {
	public long lastPlayTime = 0;
	public int PlayID = 0;
	int frameCount = 0;
	public Bitmap[] frameBitmap = null;
	public boolean isLoop = false;
	public boolean isEnd = false;
	public static int ANIM_INTERVAL = 30;

	public Anim(Context context, int[] frameBitmapID, boolean isLoop) {

		// TODO Auto-generated constructor stub
		frameCount = frameBitmapID.length;
		frameBitmap = new Bitmap[frameCount];
		for (int i = 0; i < frameCount; i++) {
			frameBitmap[i] = ReadBitMap(context, frameBitmapID[i]);
		}
		this.isLoop = isLoop;
	}

	public Anim(Context context, Bitmap[] frameBitmap, boolean isLoop) {

		// TODO Auto-generated constructor stub
		frameCount = frameBitmap.length;
		this.frameBitmap = frameBitmap;
		this.isLoop = isLoop;
	}

	private Bitmap ReadBitMap(Context context, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reset() {
		lastPlayTime = 0;
		PlayID = 0;
		isEnd = false;
	}

	// 绘制动画的每一帧
	public void DrawFrame(Canvas canvas, Paint paint, int x, int y, int frameID) {
		canvas.drawBitmap(frameBitmap[frameID], x, y, paint);

	}

	public void DrawAnimation(Canvas canvas, Paint paint, int x, int y) {
		if (!isEnd) {
			canvas.drawBitmap(frameBitmap[PlayID], x, y, paint);
			long time = System.currentTimeMillis();
			if (time - lastPlayTime > ANIM_INTERVAL) {
				PlayID++;
				lastPlayTime = time;
				if (PlayID > frameCount) {
					isEnd = true;
					if (isLoop) {
						isEnd = false;
						PlayID = 0;
					}
				}
			}
		}
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
}
