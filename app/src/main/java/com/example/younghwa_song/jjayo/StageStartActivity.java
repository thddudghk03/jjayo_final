package com.example.younghwa_song.jjayo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StageStartActivity extends AppCompatActivity {

    TextView stage;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_start);

        stage = findViewById(R.id.stage);

        Intent intent = getIntent();
        number = intent.getIntExtra("Stage", 0);

        stage.setText("Stage " + number);



        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Intent intent2 = new Intent(getApplicationContext(), VideoPlayActivity.class);
                intent2.putExtra("Stage", number);
                startActivity(intent2);
            }
        }).start();
    }
}
