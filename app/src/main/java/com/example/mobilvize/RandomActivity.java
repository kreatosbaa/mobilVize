package com.example.mobilvize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    int minValue, maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gorevRandom);

        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        EditText adetEditText = findViewById(R.id.adet);
        EditText minEditText = findViewById(R.id.min);
        EditText maxEditText = findViewById(R.id.max);

        try {
            maxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        int adet = Integer.parseInt(adetEditText.getText().toString());
                        minValue = Integer.parseInt(minEditText.getText().toString());
                        maxValue = Integer.parseInt(maxEditText.getText().toString());

                        linearLayout.removeAllViews();

                        for (int i = 0; i < adet; i++) {
                            addProgressBar(RandomActivity.this, linearLayout);
                        }
                        return true;
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addProgressBar(Context context, LinearLayout linearLayout) {
        int min, max, where;
        do {
            min = minValue + new Random().nextInt(maxValue - minValue);
            max = min + new Random().nextInt(maxValue - min + 1);
            where = min + new Random().nextInt(max - min + 1);
        } while (min == max || min == where || where == max);

        String percentage = Double.toString(((double) (where - min) / (max - min)) * 100);
        String[] parts = percentage.split("\\.");
        String percent = parts[0];

        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        TextView textPercentage = new TextView(context);
        textPercentage.setId(View.generateViewId());
        String str = where + " %" + percent;
        textPercentage.setText(str);
        ConstraintLayout.LayoutParams paramsPercentage = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsPercentage.topToTop = ConstraintSet.PARENT_ID;
        paramsPercentage.startToStart = ConstraintSet.PARENT_ID;
        paramsPercentage.setMargins(dpToPx(180), dpToPx(30), 0, 0);
        textPercentage.setLayoutParams(paramsPercentage);
        constraintLayout.addView(textPercentage);

        TextView textMin = new TextView(context);
        textMin.setId(View.generateViewId());
        textMin.setText(String.valueOf(min));
        ConstraintLayout.LayoutParams paramsMin = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsMin.topToTop = ConstraintSet.PARENT_ID;
        paramsMin.startToStart = ConstraintSet.PARENT_ID;
        paramsMin.setMargins(dpToPx(100), dpToPx(40), 0, 0);
        textMin.setLayoutParams(paramsMin);
        constraintLayout.addView(textMin);

        TextView textMax = new TextView(context);
        textMax.setId(View.generateViewId());
        textMax.setText(String.valueOf(max));
        ConstraintLayout.LayoutParams paramsMax = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsMax.topToTop = ConstraintSet.PARENT_ID;
        paramsMax.endToEnd = ConstraintSet.PARENT_ID;
        paramsMax.setMargins(0, dpToPx(40), dpToPx(100), 0);
        textMax.setLayoutParams(paramsMax);
        constraintLayout.addView(textMax);

        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsProgressBar = new ConstraintLayout.LayoutParams(
                dpToPx(150),
                dpToPx(20)
        );
        paramsProgressBar.topToTop = ConstraintSet.PARENT_ID;
        paramsProgressBar.startToStart = textMin.getId();
        paramsProgressBar.endToEnd = textMax.getId();
        paramsProgressBar.setMargins(dpToPx(30), dpToPx(40), dpToPx(30), dpToPx(100));
        progressBar.setLayoutParams(paramsProgressBar);
        progressBar.setMin(0);
        progressBar.setProgress(Integer.parseInt(percent));
        progressBar.setMax(100);
        constraintLayout.addView(progressBar);

        linearLayout.addView(constraintLayout);
    }

    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
