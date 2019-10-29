package com.example.younghwa_song.jjayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class LoadingActivity extends AppCompatActivity {
    HashSet<WordQuiz> wordQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashSet<Word> words = new HashSet<>();
                words.add(new Word("排", "pái", "열"));
                words.add(new Word("座", "zuò", "좌석"));
                words.add(new Word("您", "nín", "당신"));
                words.add(new Word("的", "de", "~의"));
                words.add(new Word("座位", "zuòwèi", "좌석"));
                words.add(new Word("是", "shì", "~이다"));

                wordQuizzes = generateWordQuizzes(words);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoadingActivity.this, WordQuizActivity.class);
                        intent.putExtra("wordQuizzes", wordQuizzes);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();
    }

    private HashSet<WordQuiz> generateWordQuizzes(HashSet<Word> words) {
        HashSet<WordQuiz> wordQuizzes = new HashSet<>();
        ArrayList<Word> a = new ArrayList<>(words);
        Random random = new Random();
        Word question;
        HashMap<String, Word> m = new HashMap<>();

        while (wordQuizzes.size() < 10) {
            question = a.get(random.nextInt(a.size()));
            m.clear();
            m.put(question.getMeaning(), question);
            while (m.size() < 4) {
                Word w = a.get(random.nextInt(a.size()));
                m.put(w.getMeaning(), w);
            }
            m.put(question.getMeaning(), question);
            ArrayList<Word> choicesWord = new ArrayList<>(m.values());
            Collections.shuffle(choicesWord);
            if (random.nextBoolean()) {
                ArrayList<String> choices = new ArrayList<>();
                for (Word word : choicesWord) {
                    choices.add(word.getMeaning());
                }
                wordQuizzes.add(new WordQuiz(question.getCharacter(), question.getMeaning(), question.getPronunciation(), choices));
            } else {
                ArrayList<String> choices = new ArrayList<>();
                for (Word word : choicesWord) {
                    choices.add(word.getCharacter());
                }
                wordQuizzes.add(new WordQuiz(question.getMeaning(), question.getCharacter(), "", choices));
            }
        }
        return wordQuizzes;
    }
}
