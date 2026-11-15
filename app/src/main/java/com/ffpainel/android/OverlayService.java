package com.ffpainel.android;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverlayService extends Service {

    private WindowManager wm;
    private WindowManager.LayoutParams params;
    private LinearLayout panel;
    private boolean minimized = false;

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 40;
        params.y = 150;

        createPanel();
    }

    private void createPanel() {
        panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(15, 10, 15, 10);
        panel.setBackgroundColor(Color.rgb(25, 25, 25));

        TextView title = new TextView(this);
        title.setText("FF PAINEL");
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);

        Button minimize = new Button(this);
        minimize.setText("Minimizar");

        Button close = new Button(this);
        close.setText("Fechar");

        panel.addView(title);
        panel.addView(minimize);
        panel.addView(close);

        minimize.setOnClickListener(v -> {
            minimized = !minimized;
            title.setText(minimized ? "FF +" : "FF PAINEL");
            minimize.setVisibility(
                minimized ? View.GONE : View.VISIBLE
            );
        });

        title.setOnTouchListener(new View.OnTouchListener() {
            private int startX, startY;
            private float touchX, touchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = params.x;
                    startY = params.y;
                    touchX = event.getRawX();
                    touchY = event.getRawY();
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    params.x = startX + (int)(event.getRawX() - touchX);
                    params.y = startY + (int)(event.getRawY() - touchY);
                    wm.updateViewLayout(panel, params);
                    return true;
                }

                return true;
            }
        });

        close.setOnClickListener(v -> stopSelf());

        wm.addView(panel, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (panel != null) {
            wm.removeView(panel);
            panel = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
