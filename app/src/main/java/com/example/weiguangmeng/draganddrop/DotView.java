package com.example.weiguangmeng.draganddrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by weiguangmeng on 15/12/28.
 */

public class DotView extends View {

    private float radius = getResources().getDimensionPixelOffset(R.dimen.radius);
    private float top = 0;
    private float left = 0;
    private float offsetX;
    private float offsetY;
    private Paint paint;
    private Context context;


    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.parseColor("#0000df"));
        paint.setAntiAlias(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float eventX = event.getX();
        float eventY = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!((left - 20) < eventX && eventX < (left + 2*radius + 20) && (top -20) < eventY &&(top + 20 + 2* radius) > eventY))
                    return  false;
                offsetX = eventX - left;
                offsetY = eventY - top;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                left = eventX - offsetX;
                top = eventY - offsetY;
                if(action == MotionEvent.ACTION_UP) {
                    checkIn(eventX, eventY);
                }
                break;
        }

        invalidate();
        return true;
    }

    private void checkIn(float x, float y) {
        LinearLayout countLayout =((LinearLayout)((MainActivity)context).findViewById(R.id.counter));
        int count = countLayout.getChildCount();
        for(int i = 0 ; i < count; i++) {
            View view  = countLayout.getChildAt(i);
            if(view.getClass() == TextView.class) {
                if(x > (view.getLeft() - 20) && x <(view.getRight() + 20)
                        && (y > (view.getTop() - 20)) && (y < (view.getBottom() + 20))) {
                    int counter = Integer.parseInt(((TextView)view).getText().toString());
                    ((TextView)view).setText( "" + (++counter));
                    left = 0;
                    top = 0;
                    break;
                }
            }
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(left + radius, top + radius, radius, paint);
    }
}


