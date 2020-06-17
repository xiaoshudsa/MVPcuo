package com.zxp.mvpcuoqv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.zxp.frame.FrameApplication;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.model.MainPageModel;
import com.zxp.mvpcuoqv.view.SubjectActivity;
import com.zxp.mvpcuoqv.view.design.BottomTabView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxp.mvpcuoqv.constants.JumpConstant.HOME_TO_SUB;
import static com.zxp.mvpcuoqv.constants.JumpConstant.JUMP_KEY;


public class HomeFragment extends BaseMvpFragment<MainPageModel> implements BottomTabView.OnBottomTabClickCallBack, NavController.OnDestinationChangedListener {
    @BindView(R.id.select_subject)
    TextView selectSubject;
    private List<Integer> normalIcon = new ArrayList<>();//为选中的icon
    private List<Integer> selectedIcon = new ArrayList<>();// 选中的icon
    private List<String> tabContent = new ArrayList<>();//tab对应的内容
    protected NavController mHomeController;
    private final int MAIN_PAGE = 1, COURSE = 2, VIP = 3, DATA = 4, MINE = 5;
    private BottomTabView mTabView;
    private String preFragment = "";
    private String mCurrentFragment = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment.findNavController(this).addOnDestinationChangedListener((controller, destination, arguments) -> {
            mCurrentFragment = destination.getLabel().toString();
            new Handler().postDelayed(() -> {
                if (preFragment.equals("DataGroupDetailFragment") && mCurrentFragment.equals("HomeFragment"))
                    mTabView.changeSelected(DATA);
                preFragment = mCurrentFragment;
            },50);
        });
    }

    @Override
    public MainPageModel setModel() {
        return null;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void setUpView() {
        mTabView = getView().findViewById(R.id.bottom_tab);
        Collections.addAll(selectedIcon, R.drawable.main_selected, R.drawable.course_selected, R.drawable.vip_selected, R.drawable.data_selected, R.drawable.mine_selected);
        Collections.addAll(tabContent, "主页", "课程", "VIP", "资料", "我的");
        Collections.addAll(normalIcon, R.drawable.main_page_view, R.drawable.course_view, R.drawable.vip_view, R.drawable.data_view, R.drawable.mine_view);
        mTabView.setResource(normalIcon, selectedIcon, tabContent);
        mTabView.setOnBottomTabClickCallBack(this);
        mHomeController = Navigation.findNavController(getView().findViewById(R.id.home_fragment_container));
        mHomeController.addOnDestinationChangedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectSubject.setText(FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_name());
    }

    @Override
    public void setUpData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        normalIcon.clear();
        selectedIcon.clear();
        tabContent.clear();
    }

    @Override
    public void clickTab(int tab) {
        switch (tab) {
            case MAIN_PAGE:
                mHomeController.navigate(R.id.mainPageFragment);
                break;
            case COURSE:
                mHomeController.navigate(R.id.courseFragment);
                break;
            case VIP:
                mHomeController.navigate(R.id.vipFragment);
                break;
            case DATA:
                mHomeController.navigate(R.id.dataFragment);
                break;
            case MINE:
                mHomeController.navigate(R.id.mineFragment);
                break;
        }
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        showLog(destination.getLabel().toString());
    }

    @OnClick({R.id.select_subject, R.id.search, R.id.message, R.id.scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_subject:
                startActivity(new Intent(getContext(), SubjectActivity.class).putExtra(JUMP_KEY, HOME_TO_SUB));
                break;
            case R.id.search:
                break;
            case R.id.message:
                break;
            case R.id.scan:
                break;
        }
    }
}
