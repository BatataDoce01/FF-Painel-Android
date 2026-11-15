package com.ffpainel.android;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.rgb(15, 18, 30));

        TextView title = new TextView(this);
        title.setText("FF PAINEL");
        title.setTextSize(30);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        TextView subtitle = new TextView(this);
        subtitle.setText("Painel demonstrativo");
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);

        Button button = new Button(this);
        button.setText("ABRIR PAINEL");
        button.setOnClickListener(v ->
            subtitle.setText("Painel aberto com sucesso!")
        );

        layout.addView(title);
        layout.addView(subtitle);
        layout.addView(button);

        setContentView(layout);
    }
          }
