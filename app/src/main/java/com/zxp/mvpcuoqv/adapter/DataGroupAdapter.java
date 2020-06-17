package com.zxp.mvpcuoqv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yiyatech.utils.newAdd.GlideUtil;
import com.zxp.data.DataGroupListEntity;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.interfaces.OnRecyclerItemClick;
import com.zxp.mvpcuoqv.view.design.RoundImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 任小龙 on 2020/6/9.
 */
public class DataGroupAdapter extends RecyclerView.Adapter<DataGroupAdapter.ViewHolder> {

    private List<DataGroupListEntity> mList;
    private Context mContext;
    public static final int ITEM_TYPE=1,FOCUS_TYPE=2;

    public DataGroupAdapter(List<DataGroupListEntity> pList, Context pContext) {
        mList = pList;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_group_list, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataGroupListEntity entity = mList.get(position);
        holder.tvName.setText(entity.getGroup_name());
        holder.tvNumber.setText(entity.getMember_num());
        holder.tvDesc.setText(entity.getIntroduce());
       /* GlideUtil.loadCornerImage(holder.ivThumb,entity.getAvatar(),null,6f);*/
        RoundedCorners roundedCorners = new RoundedCorners(50);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners).dontAnimate();
        Glide.with(mContext).load(entity.getAvatar()).apply(requestOptions).into(holder.ivThumb);
        holder.tvFocus.setText(entity.isFocus() ? "已关注" : "+关注");
        holder.tvFocus.setSelected(entity.isFocus() ? true : false);
        holder.tvFocus.setTextColor(entity.isFocus() ? ContextCompat.getColor(mContext,R.color.red) : ContextCompat.getColor(mContext,R.color.fontColorGray));
        holder.tvFocus.setOnClickListener(view->{
            if (mOnRecyclerItemClick !=null)mOnRecyclerItemClick.onItemClick(position,FOCUS_TYPE);
        });
        holder.itemView.setOnClickListener(view->{
            if(mOnRecyclerItemClick != null)mOnRecyclerItemClick.onItemClick(position,ITEM_TYPE);
        });
    }

    private static OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick){
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_focus)
        TextView tvFocus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
