package com.example.younghwa_song.jjayo;


import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;


public class WordFragment extends Fragment implements TextToSpeech.OnInitListener {
    TextView word, speak, meaning;
    public WordFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    TextToSpeech tts;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.word, container, false);
        ImageButton speech = (ImageButton) view.findViewById(R.id.speechButton);
        tts = new TextToSpeech(getActivity(), this);


        //번들에서 데이터 받기 액티비티 -> 프래그먼트 전달
        String wordAct = getArguments().getString("word");
        String wordAct2 = getArguments().getString("speak");
        String wordAct3 = getArguments().getString("mean");


        word = (TextView)view.findViewById(R.id.word);
        speak = (TextView)view.findViewById(R.id.speak);
        meaning = (TextView)view.findViewById(R.id.meaning);


        word.setText(wordAct);
        speak.setText(wordAct2);
        meaning.setText(wordAct3);

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utteranceId = this.hashCode() + "";
                int status = tts.speak(word.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                if (status == TextToSpeech.SUCCESS) {
//                    text.setText("성공");
                } else {
//                    text.setText("실패");
                }

            }
        });

        // 프래그먼트 그림판   09/30 액티비티로 화면구성 바꿈
//        MyView m = new MyView(getActivity());
//        darwLayout = (LinearLayout) view.findViewById(R.id.draw_layout);
//        darwLayout.addView(m);

        return view;
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                text.setText("지원하지 않는 언어입니다.");
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            } else {

                //음성 톤
                tts.setPitch(1.1f);
                //읽는 속도
                tts.setSpeechRate(0.6f);


//                text.setText("초기화 성공");
            }
        } else {
//            text.setText("초기화 실패");
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

}
