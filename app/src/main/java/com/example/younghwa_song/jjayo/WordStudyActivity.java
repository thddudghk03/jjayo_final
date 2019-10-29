package com.example.younghwa_song.jjayo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import me.relex.circleindicator.CircleIndicator;

public class WordStudyActivity extends AppCompatActivity {

    FragmentStatePagerAdapter fragmentStatePagerAdapter;
    static  ImageView word;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_study);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white); // 커스텀 뒤로가기
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//홈버튼
        toolbar.findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayout drawLayout = (LinearLayout) findViewById(R.id.draw_layout);
        final MyView m = new MyView(this);
        drawLayout.addView(m);

        fragmentStatePagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager vPager = (ViewPager) findViewById(R.id.vpPager);
        vPager.setAdapter(fragmentStatePagerAdapter);


        //전체 지우기
        Button clear_Drawlayout = (Button) findViewById(R.id.clear_btn);
        clear_Drawlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.points.clear();
                m.invalidate();
            }
        });


        //next 버튼
        final Button next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizStartActivity.class);
                startActivity(intent);
            }
        });


        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vPager);

        //gif 설정하는 부분
        word = (ImageView) findViewById(R.id.word_gif);

        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(word);
        Glide.with(this).load(R.drawable.pai).into(gifImage);

        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                GlideDrawableImageViewTarget gif = new GlideDrawableImageViewTarget(word);
                switch (i){
                    case 0 :
                        Glide.with(getApplicationContext()).load(R.drawable.pai).into(gif);
                        break;
                    case 1 :
                        Glide.with(getApplicationContext()).load(R.drawable.zuo).into(gif);
                        break;
                    case 2 :
                        Glide.with(getApplicationContext()).load(R.drawable.nin).into(gif);
                        break;
                    case 3 :
                        Glide.with(getApplicationContext()).load(R.drawable.de).into(gif);
                        break;
                    case 4 :
                        Glide.with(getApplicationContext()).load(R.drawable.zuowei).into(gif);
                        next_btn.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }



    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private static int NUM_ITEMS = 5;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle(5); // 액티비티와 프래그먼트 간 데이터를 주고 받기 위함
            WordFragment wf = new WordFragment();

            switch (position) {
                case 0:
                    bundle.putString("word", "排");
                    bundle.putString("speak", "pái  ");
                    bundle.putString("mean", "열");
                    wf.setArguments(bundle);
                    return wf;
                case 1:

                    bundle.putString("word", "座");
                    bundle.putString("speak", "zuò  ");
                    bundle.putString("mean", "좌석");
                    wf.setArguments(bundle);
                    return wf;
                case 2:
                    bundle.putString("word", "您");
                    bundle.putString("speak", "nín  ");
                    bundle.putString("mean", "당신");
                    wf.setArguments(bundle);
                    return wf;
                case 3 :
                    bundle.putString("word", "的");
                    bundle.putString("speak", "de");
                    bundle.putString("mean", "~의");
                    wf.setArguments(bundle);
                    return wf;
                case 4 :
                    bundle.putString("word", "座位");
                    bundle.putString("speak", "zuò‧wèi");
                    bundle.putString("mean", "좌석");
                    wf.setArguments(bundle);
                    return wf;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator 없어도 됨
        @Override
        public CharSequence getPageTitle(int position) {

            return position + "";
        }
    }
}