package com.example.createlookandroidapp;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DraggableResizableImageView extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {

    private float downX, downY;
    private float dX, dY;
    private int originalWidth, originalHeight;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private Rect rect = new Rect();

    public DraggableResizableImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public DraggableResizableImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DraggableResizableImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                dX = v.getX() - downX;
                dY = v.getY() - downY;
                originalWidth = v.getWidth();
                originalHeight = v.getHeight();
                rect.set(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    v.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                } else if (mode == ZOOM) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                    int newWidth = (int) (originalWidth + (event.getRawX() - downX));
                    int newHeight = (int) (originalHeight + (event.getRawY() - downY));
                    layoutParams.width = newWidth;
                    layoutParams.height = newHeight;
                    v.setLayoutParams(layoutParams);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }
        return true;
    }
}