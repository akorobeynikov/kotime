package ru.softstone.kotime.presentation.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import com.google.android.material.button.MaterialButton;

public final class HoldRepeatButton extends MaterialButton {
    private static final long INITIAL_REPEAT_DELAY = 500;
    private static final long REPEAT_INTERVAL = 200;

    private Runnable repeatClickWhileButtonHeldRunnable;

    public HoldRepeatButton(Context context) {
        super(context);
        init();
    }

    public HoldRepeatButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HoldRepeatButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        repeatClickWhileButtonHeldRunnable = () -> {
            performClick();
            postDelayed(repeatClickWhileButtonHeldRunnable, REPEAT_INTERVAL);
        };
        setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                setPressed(true);
                removeCallbacks(repeatClickWhileButtonHeldRunnable);
                postDelayed(repeatClickWhileButtonHeldRunnable, INITIAL_REPEAT_DELAY);
            } else if (action == MotionEvent.ACTION_UP) {
                setPressed(false);
                performClick();
                removeCallbacks(repeatClickWhileButtonHeldRunnable);
            } else if (action == MotionEvent.ACTION_CANCEL) {
                setPressed(false);
                removeCallbacks(repeatClickWhileButtonHeldRunnable);
            }
            return true;
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(repeatClickWhileButtonHeldRunnable);
        super.onDetachedFromWindow();
    }
}
