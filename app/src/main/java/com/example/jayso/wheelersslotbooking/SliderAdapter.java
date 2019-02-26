package com.example.jayso.wheelersslotbooking;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.*;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter
{

    int x = 0;
    private static ViewPager mPager1;
    Context context;
    LayoutInflater inflater;
    public int[] imgarray = {R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5};
    public String[] titleArray = {"Here, is an safe parking!!!","Information Required!!!","Unique Facalities!!!","Pay Charges Offline!!!","Extra Feature of Cancel Booking!!!"};
    public String[] descarray = {"Our Wheelers Slot Booking System providing parking place very easily to the customer from home.",
            "For registration user will have to fill the basic information. After they can search their location and see capacity of the particular place",
            "User can park multiple vehicles like 2, 3 and 4 wheels. They can add multiple vehicles from their single account.",
            "User will have to pay charges by time duration and vehicle type. User can extend their time and for that they will have to pay extra charges.",
            "Here,we are providing different types of packages. User will get discount on it.They also can cancel their booking."
    };

    public int[] bgcolor = {Color.rgb(55,55,55),
            Color.rgb(55,55,55),
            Color.rgb(55,55,55),
            Color.rgb(55,55,55),
            Color.rgb(55,55,55)};

    public SliderAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return (view==object);
    }

    @Override
    public int getCount() {
        return imgarray.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        try {
            super.destroyItem(container, position, object);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.slider_adapter,container,false);
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
        ImageView imageView = view.findViewById(R.id.sliderImg);
        TextView t1_title = view.findViewById(R.id.txttitle);
        TextView t2_desc = view.findViewById(R.id.txtdesc);
        linearLayout.setBackgroundColor(bgcolor[position]);
        imageView.setImageResource(imgarray[position]);
        t1_title.setText(titleArray[position]);
        t2_desc.setText(descarray[position]);
        container.addView(view);
        return view;
    }



}