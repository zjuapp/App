package com.near.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class MyView extends View {
	private Paint paint;
	public MyView(Context context){
		super(context);
		paint = new Paint();
		paint.setAntiAlias(true);
		this.setKeepScreenOn(true);
		paint.setColor(Color.RED);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		Rect rect = new Rect(30,30,50,50); //这里最后两个参数不是宽高、而是矩形右下角的坐标
		canvas.drawRect(rect, paint);
		RectF rectF = new RectF(70f,30f,90f,90f);//RectF 只是矩形 float形式 只是跟Rect精确度不一样
		canvas.drawArc(rectF, 0, 360, true, paint);
		canvas.drawCircle(150, 30, 20, paint);//这也是画圆 第三个参数为半径
		float[] points =new float[]{200f,10f,200f,40f,300f,30f,400f,70f};
		canvas.drawLines(points, paint);
//		canvas.drawLines(points, 1, 4, paint);//选取特定点数组中两点来画出一条直线
		canvas.drawText("Himi", 230, 30, paint);
		
	}
	

}
