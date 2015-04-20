package com.example.shootinggame;

import java.io.InputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceViewActivity extends Activity {
	AnimView animView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Display display = getWindowManager().getDefaultDisplay();
		animView = new AnimView(this, display.getWidth(), display.getHeight());
		//
		setContentView(animView);
	}

	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//animView.UpdateTouchEvent(x, y, true);
			break;
		case MotionEvent.ACTION_MOVE:
			 animView.UpdateTouchEvent(x, y, true);
			break;
		case MotionEvent.ACTION_UP:
			animView.UpdateTouchEvent(x, y, false);
			break;
		}

		return false;

	}

}
