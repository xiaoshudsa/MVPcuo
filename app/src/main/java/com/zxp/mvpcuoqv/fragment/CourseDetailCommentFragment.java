package com.zxp.mvpcuoqv.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxp.data.BaseInfo;
import com.zxp.data.CourseDetailInfo;
import com.zxp.data.LessonComment;
import com.zxp.data.SearchItemEntity;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.CourseDescribeAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;
import com.zxp.mvpcuoqv.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 任小龙 on 2020/6/21.
 */
public class CourseDetailCommentFragment extends BaseMvpFragment<CourseModel> implements  DataInterfaces {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CourseDetailInfo mCourseDetailInfo;
    private SearchItemEntity mItemInfo;
    private String mTitle;
    private int page = 1;
    private List<LessonComment> mCommentList = new ArrayList<>();
    private CourseDescribeAdapter mAdapter;

    public static CourseDetailCommentFragment getInstance(CourseDetailInfo courseInfo, SearchItemEntity itemInfo, String title) {
        CourseDetailCommentFragment fragment = new CourseDetailCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantKey.COURSE_COMMENT_DETAIL, courseInfo);
        bundle.putSerializable(ConstantKey.COURSE_COMMENT_ITEM, itemInfo);
        bundle.putString(ConstantKey.COURSE_LIST_HOMELIVE_ID, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourseDetailInfo = getArguments().getParcelable(ConstantKey.COURSE_COMMENT_DETAIL);
            mItemInfo = (SearchItemEntity) getArguments().getSerializable(ConstantKey.COURSE_COMMENT_ITEM);
            mTitle = getArguments().getString(ConstantKey.COURSE_LIST_HOMELIVE_ID);
        }
    }

    @Override
    public CourseModel setModel() {
        return new CourseModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecyclerView(recyclerView, refreshLayout, this);
        mAdapter = new CourseDescribeAdapter(getContext(), mCommentList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.COURSE_COMMENT, LoadTypeConfig.NORMAL, mItemInfo.getLesson_id(), mItemInfo.getType(), page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        BaseInfo<List<LessonComment>> baseInfo = (BaseInfo<List<LessonComment>>) pD[0];
        int loadType = (int) ((Object[]) pD[1])[0];
        if (baseInfo.isSuccess()) {
            if (loadType == LoadTypeConfig.MORE) refreshLayout.finishLoadMore();
            if (loadType == LoadTypeConfig.REFRESH) {
                refreshLayout.finishRefresh();
                mCommentList.clear();
            }
            mCommentList.addAll(baseInfo.result);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            mPresenter.getData(ApiConfig.COURSE_COMMENT, LoadTypeConfig.REFRESH, mItemInfo.getLesson_id(), mItemInfo.getType(), 1);
        } else {
            page += 1;
            mPresenter.getData(ApiConfig.COURSE_COMMENT, LoadTypeConfig.MORE, mItemInfo.getLesson_id(), mItemInfo.getType(), page);
        }
    }
}
