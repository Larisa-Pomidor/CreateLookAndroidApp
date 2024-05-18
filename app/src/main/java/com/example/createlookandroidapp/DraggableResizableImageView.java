package com.example.createlookandroidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DraggableResizableImageView extends androidx.appcompat.widget.AppCompatImageView {

    private float downX, downY;
    private float dX, dY;
    private int originalWidth, originalHeight;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private boolean isLongClick;
    private Handler handler;
    private Runnable longClickRunnable;

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
        setLongClickable(true);
        handler = new Handler();
        longClickRunnable = () -> {
            isLongClick = true;
            new AlertDialog.Builder(getContext())
                    .setTitle("Warning")
                    .setMessage("Do you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ((FrameLayout) getParent()).removeView(DraggableResizableImageView.this);
                    })
                    .setNegativeButton("No", null)
                    .show();
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                dX = getX() - downX;
                dY = getY() - downY;
                originalWidth = getWidth();
                originalHeight = getHeight();
                mode = DRAG;
                isLongClick = false;
                handler.postDelayed(longClickRunnable, 500); // 500ms for long click
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                handler.removeCallbacks(longClickRunnable);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isLongClick) {
                    return true;
                }
                if (mode == DRAG) {
                    handler.removeCallbacks(longClickRunnable);
                    animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                } else if (mode == ZOOM) {
                    handler.removeCallbacks(longClickRunnable);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
                    int newWidth = (int) (originalWidth + (event.getRawX() - downX));
                    int newHeight = (int) (originalHeight + (event.getRawY() - downY));
                    layoutParams.width = newWidth;
                    layoutParams.height = newHeight;
                    setLayoutParams(layoutParams);
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                handler.removeCallbacks(longClickRunnable);
                mode = NONE;
                return true;
        }
        return super.onTouchEvent(event);
    }
}