package com.zxp.mvpcuoqv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yiyatech.utils.newAdd.GlideUtil;
import com.zxp.data.SpecialtyChooseEntity;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.view.design.RoundImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<SpecialtyChooseEntity> mList;
    private Context mContext;

    public SubjectAdapter(List<SpecialtyChooseEntity> pList, Context pContext) {
        mList = pList;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_child_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialtyChooseEntity entity = mList.get(position);
        GlideUtil.loadImage(holder.leftRoundImage,entity.getIcon());
        holder.itemTitle.setText(entity.getBigspecialty());
        holder.itemRecyclerview.setLayoutManager(new GridLayoutManager(mContext,4));
        holder.itemRecyclerview.setAdapter(new SubjectChildAdapter(entity.getData(),mContext,this));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_round_image)
        RoundImage leftRoundImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_recyclerview)
        RecyclerView itemRecyclerview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
