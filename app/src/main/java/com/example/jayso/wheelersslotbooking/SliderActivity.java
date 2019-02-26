package com.example.jayso.wheelersslotbooking;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    View view;
    private Context context;
    LinearLayout layout_similar;
    private static ViewPager mPager1;
    int x = 0;

    int img_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

       /* ViewPager viewPager = findViewById(R.id.viwpager);
        SliderAdapter sliderAdapter = new SliderAdapteer(this);
        viewPager.setAdapter(sliderAdapter);*/
        bindid();
//        Button btnskip = findViewById(R.id.skip_btn);
//        btnskip.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent it = new Intent(SliderActivity.this, LoginSignupActivity.class);
//                startActivity(it);
//            }
//        });
    }

    private void bindid() {
        // TODO Auto-generated method stub
        context = SliderActivity.this;
        String[] img_hint = new String[5];
//        if (!ApplicationPreferences.getValue("HINT_IMG", "0", StartScreen_Activity.this).split(",").equals("0")) {
//            img_hint = ApplicationPreferences.getValue("HINT_IMG", "0", StartScreen_Activity.this).split(",");
//        }


//        if (img_hint != null) {

//            img_count = 5;
        mPager1 = (ViewPager) findViewById(R.id.viewpager);
        final TextView Btn_Next = (TextView) findViewById(R.id.Btn_Next);
        final TextView Btn_Skip = findViewById(R.id.Btn_Skip);
//            final TextView next_start_btn =  findViewById(R.id.Btn_);
        final TextView Btn_Prev = (TextView) findViewById(R.id.Btn_Prev);
        mPager1.setAdapter(new SliderAdapter(SliderActivity.this));
//            CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.first_screen_circle_indicator);
//            indicator.setPageColor(getResources().getColor(R.color.actionbar_opacity));
//            indicator.setViewPager(mPager1);
//            final float density = getResources().getDisplayMetrics().density;
//            indicator.setRadius(5 * density);

        mPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                toast(position+"");

                x = position;
                if (position > 3) {
                    Btn_Next.setVisibility(View.GONE);
                    Btn_Skip.setVisibility(View.VISIBLE);
                } else {
                    Btn_Next.setVisibility(View.VISIBLE);
                    Btn_Skip.setVisibility(View.GONE);
                }
                if (position == 0) {
                    Btn_Next.setVisibility(View.VISIBLE);
                    Btn_Prev.setVisibility(View.GONE);
                } else {
//                        Btn_next.setVisibility(View.GONE);
                    Btn_Prev.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (img_hint.length == 1) {
//                next_start_btn.setVisibility(View.GONE);
            Btn_Skip.setVisibility(View.VISIBLE);
        }


//            next_start_btn
        Btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (x > (img_count - 1)) {
                    x--;
                    mPager1.setCurrentItem(x);
                }
                if (x >= 0) {
                    Btn_Next.setVisibility(View.VISIBLE);
                    Btn_Prev.setVisibility(View.VISIBLE);
                }
                if (x == 0) {
                    Btn_Next.setVisibility(View.VISIBLE);
                    Btn_Skip.setVisibility(View.GONE);
                    Btn_Prev.setVisibility(View.GONE);
                }
            }
        });


//        Btn_Skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (x > (img_count - 1)) {
//                    x++;
//                    mPager1.setCurrentItem(x);
//                }
//                if (x > 0) {
//                    Btn_Next.setVisibility(View.VISIBLE);
//                    Btn_Prev.setVisibility(View.GONE);
//                }
//                if (x == (img_count - 1)) {
//                    Btn_Next.setVisibility(View.GONE);
//                    Btn_Skip.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        Btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (x >= 0) {
                    x++;
                    mPager1.setCurrentItem(x);
                }

                if (x == 0) {
                    Btn_Next.setVisibility(View.VISIBLE);
                    Btn_Prev.setVisibility(View.GONE);
                }
                if (x == (img_count - 1)) {
                    Btn_Next.setVisibility(View.VISIBLE);
                    Btn_Skip.setVisibility(View.GONE);
                }
                    /*if (x == 2) {
                        next_start_btn.setVisibility(View.VISIBLE);
                        next_start_Skip.setVisibility(View.GONE);
                    }*/
            }
        });

        Btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    ApplicationPreferences.setValue("START_PAGE", "0", StartScreen_Activity.this);
                Intent i = new Intent(context, LoginSignupActivity.class);
//                    ApplicationPreferences.setValue("From_Shopping", "0", StartScreen_Activity.this);
                startActivity(i);
                finish();
            }
        });

//        } else {
//            Log.e("", "img null");
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

