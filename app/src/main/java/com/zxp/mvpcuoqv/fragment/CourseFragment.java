package com.zxp.mvpcuoqv.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.MyFragmentAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.model.CourseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;



/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends BaseMvpFragment<CourseModel> {

    public static final int TRAINTAB = 3;
    public static final int BESTTAB = 1;
    public static final int PUBLICTAB = 2;

    @BindView(R.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mFragmentAdapter;
    @Override
    public CourseModel setModel() {
        return new CourseModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    public void setUpView() {
        mFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, titleList);
        viewPager.setAdapter(mFragmentAdapter);
        slideLayout.setViewPager(viewPager);
    }

    @Override
    public void setUpData() {
        Collections.addAll(titleList, "训练营", "精品课", "公开课");
        Collections.addAll(mFragments,CourseChildFragment.getInstance(TRAINTAB),CourseChildFragment.getInstance(BESTTAB),CourseChildFragment.getInstance(PUBLICTAB));
        mFragmentAdapter.notifyDataSetChanged();
        slideLayout.notifyDataSetChanged();
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}
