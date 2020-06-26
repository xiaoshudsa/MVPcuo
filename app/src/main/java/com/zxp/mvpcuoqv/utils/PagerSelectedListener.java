package com.zxp.mvpcuoqv.utils;

import androidx.viewpager.widget.ViewPager;

public  interface PagerSelectedListener extends ViewPager.OnPageChangeListener {
/*

    public void onPageScrollStateChanged( int   p) {
    }

    public void  onPageScrolled(int  position,  float  positionOffset,  int  positionOffsetPixels) {
    }

    public void onPageSelected( int  position) {
        pageSelected(position);
    }
*/
    public  void  pageSelected(int pPosition);
}
