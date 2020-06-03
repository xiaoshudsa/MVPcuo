package com.zxp.mvpcuoqv.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.fragment.Fragmenta;
import com.zxp.mvpcuoqv.fragment.Fragmentb;
import com.zxp.mvpcuoqv.fragment.Fragmentc;
import com.zxp.mvpcuoqv.fragment.Fragmentd;
import com.zxp.mvpcuoqv.fragment.Fragmentf;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private NoScrollVIewPager vp_shi;
    private TabLayout tl_shi;
    private ImageView iv_tu;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        vp_shi = (NoScrollVIewPager) findViewById(R.id.vp_shi);
        tl_shi = (TabLayout) findViewById(R.id.tl_shi);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragmenta());
        fragments.add(new Fragmentb());
        fragments.add(new Fragmentc());
        fragments.add(new Fragmentd());
        fragments.add(new Fragmentf());
        vp_shi.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tl_shi.setupWithViewPager(vp_shi);
        tl_shi.getTabAt(0).setCustomView(lauout("主页",R.drawable.stroke0));
        tl_shi.getTabAt(1).setCustomView(lauout("课程",R.drawable.stroke1));
        tl_shi.getTabAt(2).setCustomView(lauout("VIP",R.drawable.stroke2));
        tl_shi.getTabAt(3).setCustomView(lauout("资料",R.drawable.stroke4));
        tl_shi.getTabAt(4).setCustomView(lauout("我的",R.drawable.stroke3));

    }

    private View lauout(String url, int r) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_tablayout, null);
        iv_tu = inflate. findViewById(R.id.iv_tu);

        tv_title = inflate.findViewById(R.id.tv_tie);
       tv_title.setText(url);
       iv_tu.setImageResource(r);
       return inflate;
    }
}
