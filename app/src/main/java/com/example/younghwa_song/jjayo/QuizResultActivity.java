package com.example.younghwa_song.jjayo;

import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResultActivity extends Activity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        text = findViewById(R.id.text);
        String s = getIntent().getStringExtra("result");
        text.setText(s);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                finish();
            }
        }).start();
    }
}
