package com.example.zhuyikun.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.zhuyikun.myapplication.Views.EmotionRainAnimation;
import com.example.zhuyikun.myapplication.Views.FloatDialog;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private FrameLayout mEmotionRainContainer;
    private EmotionRainAnimation rainAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FloatDialog dialog = new FloatDialog();
//        dialog.setOutContext(this);
//        dialog.show(getSupportFragmentManager(), getLocalClassName());
        mEmotionRainContainer = (FrameLayout) findViewById(R.id.emotion_rain_container);
        rainAnimation = new EmotionRainAnimation(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        rainAnimation.setLayoutParams(params);
        mEmotionRainContainer.addView(rainAnimation);
        mButton = (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rainAnimation.startAnimation();
            }
        });
    }
}

