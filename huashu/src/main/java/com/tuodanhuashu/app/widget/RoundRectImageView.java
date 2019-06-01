package com.tuodanhuashu.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.R;


public class RoundRectImageView extends View {
    private float width;
    private float height;


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    Bitmap bitmap;
    RectF savedArea = new RectF();
    float radiusX1, radiusX2;
    float radiusY1, radiusY2;
    private float[] radiusArray;


    public RoundRectImageView(Context context) {
        this(context, null);

    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView);
        radiusX1 = ta.getFloat(R.styleable.RoundRectImageView_radiusX1, 0f);
        radiusY1 = ta.getFloat(R.styleable.RoundRectImageView_radiusY1, 0f);
        radiusX2 = ta.getFloat(R.styleable.RoundRectImageView_radiusX2, 0f);
        radiusY2 = ta.getFloat(R.styleable.RoundRectImageView_radiusY2, 0f);
        ta.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        bitmap = getInitialBitmap((int) width, (int) height);
        savedArea.set(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radiusArray = new float[]{DisplayUtil.dp2px(radiusX1), DisplayUtil.dp2px(radiusX1), DisplayUtil.dp2px(radiusX2), DisplayUtil.dp2px(radiusX2),
                DisplayUtil.dp2px(radiusY2), DisplayUtil.dp2px(radiusY2), DisplayUtil.dp2px(radiusY1), DisplayUtil.dp2px(radiusY1)};
        Bitmap bitmapFrame = makeRoundRectFrame((int) width, (int) height);//图片形状

        int saved = canvas.saveLayer(savedArea, paint);

        canvas.drawBitmap(bitmapFrame, 0, 0, paint);
        paint.setXfermode(xfermode);
        Log.d("111",bitmap.toString());
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    Bitmap getInitialBitmap(int width, int height) {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    }


    //绘制图片形状
    private Bitmap makeRoundRectFrame(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, w, h), radiusArray, Path.Direction.CW);
        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(Color.WHITE);
        c.drawPath(path, bitmapPaint);
        return bm;
    }

    //将Drawalbe转换为Bitmap
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //设置图片的接口
    public void setDrawable(Drawable resource) {

        bitmap = drawableToBitmap(resource);
        invalidate();
    }
}