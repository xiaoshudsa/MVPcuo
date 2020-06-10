package com.zxp.mvpcuoqv.adapter;

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
import com.zxp.data.VipListBean;
import com.zxp.data.VipLiveBean;
import com.zxp.mvpcuoqv.R;

import java.util.List;

public class VipGirdeAdapter extends RecyclerView.Adapter<VipGirdeAdapter.ViewHolde> {

    private List<VipListBean.ListBean> mListBeans;
    private Context mContext;

    public VipGirdeAdapter( List<VipListBean.ListBean> pListBeans, Context pContext) {
        mListBeans = pListBeans;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolde(LayoutInflater.from(mContext).inflate(R.layout.activity_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {

        VipListBean.ListBean listBean = mListBeans.get(position);
        holder.mTextView.setText(listBean.getLesson_name());
        holder.mTextnum.setText(listBean.getLength());
        holder.mTextfeedback.setText(listBean.getComment_rate());
        Glide.with(mContext).load(listBean.getVip_thumb()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        TextView mTextnum;
        TextView mTextfeedback;
        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_img);
            mTextView = itemView.findViewById(R.id.text_title);
            mTextnum = itemView.findViewById(R.id.text_num);
            mTextfeedback = itemView.findViewById(R.id.tv_feedback);
        }
    }
}
