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
import com.yiyatech.utils.newAdd.GlideUtil;
import com.zxp.data.EssenceBean;
import com.zxp.mvpcuoqv.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EssenceAdapter extends RecyclerView.Adapter<EssenceAdapter.ViewHolde> {


    private List<EssenceBean> mEssenceList;
    private Context mContext;

    public EssenceAdapter(List<EssenceBean> pEssenceList, Context pContext) {
        mEssenceList = pEssenceList;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolde(LayoutInflater.from(mContext).inflate(R.layout.activity_live_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        EssenceBean essenceBean = mEssenceList.get(position);
       holder.textTitle.setText(essenceBean.getTitle());
       holder.tvBrowseNum.setText(essenceBean.getView_num()+"人浏览");
       holder.tvCommentNum.setText(essenceBean.getReply_num()+"人跟贴");
        GlideUtil.loadCornerImage(holder.liveImg,essenceBean.getPic(),null,6f);
        holder.textTiem.setText(essenceBean.getCreate_time());
    }

    @Override
    public int getItemCount() {
        return mEssenceList.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.live_img)
        ImageView liveImg;
        @BindView(R.id.tv_browse_num)
        TextView tvBrowseNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.text_tiem)
        TextView textTiem;
        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
