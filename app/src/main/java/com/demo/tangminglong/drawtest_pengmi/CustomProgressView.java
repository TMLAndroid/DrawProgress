package com.demo.tangminglong.drawtest_pengmi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by tangminglong on 17/10/26.
 */

public class CustomProgressView extends View{

    private static  int View_RADIUS = 120;//整个View的半径  自己根据需要调整 dp

    private static  int OUTER_RADIUS = View_RADIUS - 15;
    private static  int INNER_RADIUS = View_RADIUS - 20;
    private static  int textPadding = 5;
    Paint paint = new Paint();
    Path path = new Path();
    private String topStr = "已学习生词";
    private String middleStr = "136个";
    private String bottomStr = "未学习生词24个";
    private Xfermode xfermode;

    public CustomProgressView(Context context) {
        super(context);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        OUTER_RADIUS = dp2px(getContext(), OUTER_RADIUS);
        INNER_RADIUS = dp2px(getContext(), INNER_RADIUS);
        View_RADIUS = dp2px(getContext(), View_RADIUS);
        textPadding = dp2px(getContext(), textPadding);
        paint.setAntiAlias(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2*View_RADIUS,2*View_RADIUS);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawInnerCircle(canvas);
        drawOuterCircle(canvas);
        drawText(canvas);


    }

    /**
     * 绘制内圆
     * @param canvas
     */
    private void drawInnerCircle(Canvas canvas) {
        paint.setColor(Color.parseColor("#805f5f5f"));
        paint.setStyle(Paint.Style.FILL);
        int saved = canvas.saveLayer(null,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(View_RADIUS,View_RADIUS, INNER_RADIUS,paint);
        paint.setXfermode(xfermode);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(View_RADIUS, (float) (View_RADIUS + Math.sqrt(2)*View_RADIUS),INNER_RADIUS,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);

    }

    /**
     * 绘制外边框
     * @param canvas
     */
    private void drawOuterCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        int leftWidth = View_RADIUS-OUTER_RADIUS;
        RectF rectF = new RectF(leftWidth,leftWidth,OUTER_RADIUS*2 + leftWidth,OUTER_RADIUS*2 + leftWidth);
        path.addArc(rectF,135,180);
        //canvas.drawArc(rectF,135,180,false,paint);
        path.rLineTo(-dp2px(getContext(),14),dp2px(getContext(),14));
        canvas.drawPath(path,paint);


    }

    /**
     * 绘制中间的文字
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int middleY = getHeight() / 2;
        int middleX = getWidth() / 2;
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(true);
        paint.setTextSize(80);
        float middleStrWidth = paint.measureText(middleStr);
        //Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Rect rectMiddle = new Rect();
        paint.getTextBounds(middleStr,0,middleStr.length(),rectMiddle);
        float middleLineHeight = (rectMiddle.bottom -rectMiddle.top);
        paint.setColor(Color.WHITE);
        canvas.drawText(middleStr,middleX - middleStrWidth/2,middleY + middleLineHeight/2,paint);


        paint.setTextSize(40);
        paint.setFakeBoldText(false);
        Rect rect = new Rect();
        paint.getTextBounds(bottomStr,0,bottomStr.length(),rect);
        float fontSpacing = paint.getFontSpacing();
        paint.setColor(Color.parseColor("#9FDBEC"));
        float topStrWidth = paint.measureText(topStr);
        canvas.drawText(topStr,middleX - topStrWidth/2,middleY-middleLineHeight-textPadding,paint);
        float bottomStrWidth = paint.measureText(bottomStr);
        canvas.drawText(bottomStr,middleX - bottomStrWidth/2,middleY+middleLineHeight+textPadding+(rect.bottom - rect.top),paint);
    }


    /**
     * 设置上部分文字
     * @param topStr
     */
    public void setTopText(String topStr){
        this.topStr = topStr;
        invalidate();
    }

    /**
     * 设置中间部分文字
     * @param middleStr
     */
    public void setMiddleText(String middleStr){
        this.middleStr = middleStr;
        invalidate();
    }


    /**
     * 设置下部分文字
     * @param bottomStr
     */
    public void setBottomText(String bottomStr){
        this.bottomStr = bottomStr;
        invalidate();
    }



    private  int dp2px(Context context,float dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal,context.getResources().getDisplayMetrics());
    }
}
