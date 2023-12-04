package com.baidu.baiduapplication.viewholder;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;

public class RoundedCornerDrawable extends ShapeDrawable {

    private final Paint paint;
    private final RectF rectF;
    private final float cornerRadius;

    public RoundedCornerDrawable(float cornerRadius, int backgroundColor) {
        super();

        this.cornerRadius = cornerRadius;

        paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);

        rectF = new RectF();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        rectF.set(bounds);
    }
}