package com.example.mobilvize;

import android.content.Intent;
import android.view.View;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorevMain);

        Button convertButton = findViewById(R.id.convert);
        Button randomButton = findViewById(R.id.random);
        Button smsButton = findViewById(R.id.sms);

        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);

        animateFadeIn(textView, 0f, 1f, 2000);
        animateFadeIn(textView2, 0f, 1f, 3000);

        animateButton(convertButton, 0, 800);
        animateButton(randomButton, 0, 900);
        animateButton(smsButton, 0, 1000);

        setButtonClickListeners();
    }

    private void animateFadeIn(View view, float fromAlpha, float toAlpha, long duration) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha);
        fadeIn.setDuration(duration);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.start();
    }

    private void animateButton(Button button, long startDelay, long duration) {
        ObjectAnimator translateX = ObjectAnimator.ofFloat(button, "translationX", 0f, 500f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(button, "translationY", 0f, 1200f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateX, translateY);
        animatorSet.setStartDelay(startDelay);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    private void setButtonClickListeners() {
        try {
            Button convertButton = findViewById(R.id.convert);
            Button randomButton = findViewById(R.id.random);
            Button smsButton = findViewById(R.id.sms);

            convertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Conversion.class);
                    startActivity(intent);
                }
            });

            randomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RandomActivity.class);
                    startActivity(intent);
                }
            });

            smsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SmsActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
