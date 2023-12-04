package com.baidu.baiduapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class RoundedCornerFrameLayout extends RelativeLayout {

    private final Paint paint;
    private final Path clipPath;
    private final RectF rectF;
    private final float cornerRadius;

    public RoundedCornerFrameLayout(Context context) {
        this(context, null);
    }

    public RoundedCornerFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedCornerFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 读取圆角半径和背景颜色
        cornerRadius = getResources().getDimension(R.dimen.corner_radius);
        int backgroundColor =0xFFFFFFFF;

        paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);

        clipPath = new Path();
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        // 创建圆角路径
        rectF.set(0, 0, getWidth(), getHeight());
        clipPath.reset();
        clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(clipPath);

        // 绘制圆角背景
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        canvas.restore();

        super.onDraw(canvas);
    }
}