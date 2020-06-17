package com.zxp.mvpcuoqv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import com.bumptech.glide.request.RequestOptions;
import com.zxp.data.VipLiveBean;
import com.zxp.mvpcuoqv.R;

import java.util.List;

public class VipLiveAdapter extends RecyclerView.Adapter<VipLiveAdapter.ViewHolde> {

  private   List<VipLiveBean.LiveBeanX.LiveBean> mLiveBeans;
    private Context mContext;

    public VipLiveAdapter(List<VipLiveBean.LiveBeanX.LiveBean> pMliveData, Context pContext) {
        mLiveBeans = pMliveData;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolde(LayoutInflater.from(mContext).inflate(R.layout.activity_live_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        VipLiveBean.LiveBeanX.LiveBean liveBean = mLiveBeans.get(position);
        holder.mTextView.setText(liveBean.getActivityName());
        holder.mTime.setText(liveBean.getStart_date());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(mContext).load(liveBean.getCoverImgUrl()).apply(requestOptions).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mLiveBeans.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        TextView mTime;
        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.live_img);
            mTextView= itemView.findViewById(R.id.text_title);
            mTime = itemView.findViewById(R.id.text_tiem);
        }
    }
}
