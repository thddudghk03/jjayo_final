package com.example.younghwa_song.jjayo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

class Point {
    float x;
    float y;
    boolean check;
    int color;

    public Point(float x, float y, boolean check, int color) {
        this.x = x;
        this.y = y;
        this.check = check;
        this.color = color;
    }
}

class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setStrokeWidth(4);
        for (int i = 1; i < points.size(); i++) {
            p.setColor(points.get(i).color);
            if (!points.get(i).check)
                continue;
            canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, p);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                points.add(new Point(x, y, false, color));
            case MotionEvent.ACTION_MOVE:
                points.add(new Point(x, y, true, color));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }


    ArrayList<Point> points = new ArrayList<Point>();
    int color = Color.BLACK;
}