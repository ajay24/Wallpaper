package com.ajay.wallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPoint {
	

	private String text;
	private int x;
	private int y;
	//Paint paint;
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public MyPoint(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	

	public void draw(Canvas canvas, Paint paint ) {
		canvas.drawText(text, x, y, paint);
		// TODO Auto-generated method stub
		
	}
}