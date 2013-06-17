package com.ajay.wallpaper;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;

import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class MyWallpaperService extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new MyWallpaperEngine();
	}

	private class MyWallpaperEngine extends Engine {
		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			public void run() {
				draw();
			}

		};
		private List<MyPoint> mypoints;
		private Paint paint = new Paint();
		private int width;
		private String text;
		private String color;
		int height;
		private boolean visible = true;
		private int maxNumber;
		private boolean touchEnabled;
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(MyWallpaperService.this);

		OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {

			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				Toast.makeText(
						getBaseContext(),
						"No. of points set to "
								+ prefs.getString("color", "#00ff0000"),
						Toast.LENGTH_SHORT).show();
				maxNumber = Integer.valueOf(prefs.getString("numberOfPoints",
						"4"));
				mypoints.clear();
				touchEnabled = prefs.getBoolean("touch", false);
				text = prefs.getString("text", "Ajay");
				color=prefs.getString("color", "#00ff0000");
				paint.setColor(Color.parseColor(color));

			}
		};

		public MyWallpaperEngine() {

			maxNumber = Integer.valueOf(prefs.getString("numberOfPoints", "4"));

			prefs.registerOnSharedPreferenceChangeListener(listener); // on
																		// preference
																		// change
																		// listener
																		// attached
			touchEnabled = prefs.getBoolean("touch", false);
			text = prefs.getString("text", "Ajay");
			color=prefs.getString("color", "#00ff0000");
			

			mypoints = new ArrayList<MyPoint>();
			paint.setAntiAlias(true);
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(2f);
			//paint.setColor(Color.parseColor(color));

			paint.setTextSize(25);
			

			handler.post(drawRunner);
		}

		@Override//Not working right now
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
			for (MyPoint point : mypoints) {
				point.setX(point.getX()-20);

			}
			
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			this.width = width;
			this.height = height;
			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			if (touchEnabled) {

				int x = (int) event.getX();
				int y = (int) event.getY();
				SurfaceHolder holder = getSurfaceHolder();
				Canvas canvas = null;
				try {
					canvas = holder.lockCanvas();
					if (canvas != null) {
						canvas.drawColor(Color.BLACK);
						mypoints.clear();
						mypoints.add(new MyPoint(
								String.valueOf(mypoints.size() + 1), x, y));
						drawmypoints(canvas, mypoints);

					}
				} finally {
					if (canvas != null)
						holder.unlockCanvasAndPost(canvas);
				}
				super.onTouchEvent(event);
			}
		}

		private void draw() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					if (mypoints.size() >= maxNumber) {
						mypoints.remove(mypoints.size() - 1);

						// mypoints.clear();
					}
					int x = (int) (width * Math.random());
					int y = (int) (height * Math.random());
					mypoints.add(0, new MyPoint(text, x, y));
					drawmypoints(canvas, mypoints);
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 1000);
			}
		}

		// Surface view requires that all elements are drawn completely
		private void drawmypoints(Canvas canvas, List<MyPoint> mypoints) {
			canvas.drawColor(Color.BLACK);
			for (MyPoint point : mypoints) {
				point.draw(canvas, paint);

			}
		}
	}
}