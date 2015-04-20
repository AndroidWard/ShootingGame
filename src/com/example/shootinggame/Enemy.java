package com.example.shootinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.Animation;

public class Enemy {
	public static boolean ENEMY_STATE_ALIVE = true;
	public static boolean ENEMY_STATE_DEAD = false;
	public static int ENEMY_STEP_Y = 5;
	public static int ENEMY_STEP_X = 0;
	public static int BULLET_WIDTH = 40;

	public  int ENEMY_POS_Y = 0;
	public  int ENEMY_POS_X = 0;
    
	public Anim ENEMY_DEATH_ANIM=null;
	public Anim ENEMY_MOVE_ANIM=null;
	
	public boolean ANIMATION_STATE=false;
	public boolean ENEMY_FOCUS=false;
	public boolean ENEMY_STATE=false;
	Context context=null;
	public Enemy(Context context,Bitmap[] frameBitmap,Bitmap []deathBitmap) {
		
		this.context=context;
		ENEMY_MOVE_ANIM=new Anim(this.context,frameBitmap,true);  //加载敌人移动的动画（循环）
		ENEMY_DEATH_ANIM=new Anim(this.context,deathBitmap,false);  //加载敌人死亡的动画（不循环）
		
		
	}
	public void init(int x,int y)
	{
		ENEMY_POS_X=x;
		ENEMY_POS_Y=y;
		ENEMY_STATE=ENEMY_STATE_ALIVE;
		ANIMATION_STATE=ENEMY_STATE_ALIVE;
		ENEMY_FOCUS=true;
		ENEMY_DEATH_ANIM.reset();
		ENEMY_MOVE_ANIM.reset();
		
	}
public void DrawEnemy(Canvas canvas,Paint paint)
{
	if (ENEMY_FOCUS) {
		if (ANIMATION_STATE==ENEMY_STATE_ALIVE)  //播放敌人移动状态
		{
		ENEMY_MOVE_ANIM.DrawAnimation(canvas, paint, ENEMY_POS_X, ENEMY_POS_Y);
		}
		else if (ANIMATION_STATE==ENEMY_STATE_DEAD) {
			ENEMY_DEATH_ANIM.DrawAnimation(canvas, paint, ENEMY_POS_X, ENEMY_POS_Y);//播放敌人死亡状态
		}
	
     
		
	}
}
public void updateEnemy(){
	if (ENEMY_FOCUS) {
		ENEMY_POS_Y+=ENEMY_STEP_Y;
		if (ANIMATION_STATE==ENEMY_STATE_DEAD) {
			if (ENEMY_DEATH_ANIM.isEnd) {
				ENEMY_FOCUS=false;
				ENEMY_STATE=ENEMY_STATE_DEAD;
			}
		}
	}
	
}


}
