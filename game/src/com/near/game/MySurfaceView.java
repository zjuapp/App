package com.near.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder sh;
	private Thread th;
	private Paint paint;
	private Canvas canvas;
	private Resources res;
	private Bitmap bp;
	private Bitmap bp_backGround;
	private int ScreenH, ScreenW;
	private boolean flag;
	public static String str_zh = "Himi";
	public static String str_pass = "Android进阶群:109367315";
	private int bp_x, bp_y;
	private boolean flag_zh = true;
	public static String button_str = "Hello world!!";
	public MySurfaceView(Context context, AttributeSet  attr) {
		super(context);
		res = this.getResources();
		bp = BitmapFactory.decodeResource(res, R.drawable.register);
		bp_backGround = BitmapFactory.decodeResource(res, R.drawable.duola);
		th = new Thread(this);
		sh = this.getHolder();
		sh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		this.setKeepScreenOn(true);
		this.setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		ScreenW = this.getWidth();
		ScreenH = this.getHeight();
		bp_x = ScreenW / 2 - bp.getWidth() / 2;
		bp_y = ScreenH / 2 - bp.getHeight() / 2;
		th.start();
	}

	public void draw() {
		canvas = sh.lockCanvas();
		paint.setColor(Color.RED);
		if (canvas != null) {// 当点击home键 或者返回按键的时候canvas是得不到的，这里要注意
			canvas.drawColor(Color.WHITE);
			canvas.drawText(button_str, bp_x + 20, bp_y + 43 + 15, paint);
			canvas.save();
			canvas.scale(4, 4);
			canvas.restore();
			sh.unlockCanvasAndPost(canvas);
		}
	}

	public void run() {
		while (true) {
			draw();
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				Log.v("Run", "Error");
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}