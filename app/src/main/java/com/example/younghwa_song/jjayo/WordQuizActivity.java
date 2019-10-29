package com.example.younghwa_song.jjayo;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Stack;

public class WordQuizActivity extends AppCompatActivity implements View.OnClickListener {
    Stack<WordQuiz> wordQuizzes = new Stack<>();
    TextView lifeTextView, questionTextView, pronunciationTextView, remainingTextView;
    Button button1, button2, button3, button4;

    int life = 5;
    String question;
    String answer;
    String pronunciation;

    String choice1;
    String choice2;
    String choice3;
    String choice4;

    State state;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    //툴바의 뒤로가기
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.bgOff :
                Intent musicIntent1 = new Intent(getApplicationContext(), MusicService.class);
                stopService(musicIntent1);
                break;
            case R.id.bgOn :

                Intent musicIntent = new Intent(getApplicationContext(), MusicService.class);
                startService(musicIntent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    enum State {
        Solving, Finished
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white); // 커스텀 뒤로가기
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//홈버튼
        toolbar.findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        lifeTextView = findViewById(R.id.text_life);
        questionTextView = findViewById(R.id.text_question);
        pronunciationTextView = findViewById(R.id.text_pronunciation);
        remainingTextView = findViewById(R.id.text_remaining);

        button1 = findViewById(R.id.button_choice_1);
        button2 = findViewById(R.id.button_choice_2);
        button3 = findViewById(R.id.button_choice_3);
        button4 = findViewById(R.id.button_choice_4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        Serializable serializable = getIntent().getSerializableExtra("wordQuizzes");
        if (serializable instanceof HashSet<?>) {
            for (Object obj : (HashSet<?>) serializable) {
                if (obj instanceof WordQuiz) {
                    wordQuizzes.push((WordQuiz) obj);
                }
            }
        }

        initialize();
    }


    private void initialize() {
        life = 5;
        state = State.Solving;
        setFields();
        display();
    }

    private void setFields() {
        if (state == State.Solving) {
            WordQuiz wordQuiz = wordQuizzes.pop();

            question = wordQuiz.getQuestion();
            answer = wordQuiz.getAnswer();
            pronunciation = wordQuiz.getPronunciation();

            choice1 = wordQuiz.getChoices().get(0);
            choice2 = wordQuiz.getChoices().get(1);
            choice3 = wordQuiz.getChoices().get(2);
            choice4 = wordQuiz.getChoices().get(3);
        }
    }

    private void display() {
        if (state == State.Solving) {
            String s = "♥ × " + life;
            lifeTextView.setText(s);
            questionTextView.setText(question);
            pronunciationTextView.setText(pronunciation);
            s = "남은 퀴즈 : " + wordQuizzes.size();
            remainingTextView.setText(s);

            button1.setText(choice1);
            button2.setText(choice2);
            button3.setText(choice3);
            button4.setText(choice4);
        }
    }

    @Override
    public void onClick(View v) {
        if (state == State.Solving) {
            String choice;
            switch (v.getId()) {
                case R.id.button_choice_1:
                    choice = choice1;
                    break;
                case R.id.button_choice_2:
                    choice = choice2;
                    break;
                case R.id.button_choice_3:
                    choice = choice3;
                    break;
                case R.id.button_choice_4:
                    choice = choice4;
                    break;
                default:
                    return;
            }
            Intent intent = new Intent(this, QuizResultActivity.class);
            if (!choice.equals(answer)) {
                life--;
                intent.putExtra("result", "오답...");
            } else {
                intent.putExtra("result","정답!");
            }
            startActivity(intent);
            if (life > 0 && wordQuizzes.size() > 0) {
                setFields();
                display();
            } else {
                display();
                state = State.Finished;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), QuizStartActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }).start();
            }
        }
    }
}

