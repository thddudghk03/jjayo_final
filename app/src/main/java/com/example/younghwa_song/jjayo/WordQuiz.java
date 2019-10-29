package com.example.younghwa_song.jjayo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class WordQuiz implements Serializable {
    private String question;
    private String answer;
    private String pronunciation;
    private ArrayList<String> choices;

    WordQuiz(String question, String answer, String pronunciation, ArrayList<String> choices) {
        this.question = question;
        this.answer = answer;
        this.pronunciation = pronunciation;
        this.choices = choices;
    }

    String getQuestion() {
        return question;
    }

    String getAnswer() {
        return answer;
    }

    String getPronunciation() {
        return pronunciation;
    }

    ArrayList<String> getChoices() {
        return choices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordQuiz wordQuiz = (WordQuiz) o;
        return Objects.equals(question, wordQuiz.question) &&
                Objects.equals(answer, wordQuiz.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }
}
