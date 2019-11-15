package com.example.ex22_pager;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    ViewPager pager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager1 = findViewById(R.id.pager1);
        // 기본 3개 : 다음으로 숫자 조정
        pager1.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter();
        pager1.setAdapter(adapter);
    }

    public void onBtn1Clicked(View v){
        pager1.setCurrentItem(0);
    }

    public void onBtn2Clicked(View v){
        pager1.setCurrentItem(1);
    }

    public void onBtn3Clicked(View v){
        pager1.setCurrentItem(2);
    }

    class MyPagerAdapter extends PagerAdapter{
        String[] names = {"홍길동", "강감찬", "을지문덕"};

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

            pager1.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(getApplicationContext());
            textView.setText(names[position]);
            textView.setTextSize(40.0f);
            textView.setTextColor(Color.BLUE);

            layout.addView(textView);
            pager1.addView(layout, position);

            return layout;
        }
    }
}
