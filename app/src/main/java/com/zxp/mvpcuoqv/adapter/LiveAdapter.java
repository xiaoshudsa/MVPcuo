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
import com.zxp.data.BannerLiveInfo;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.fragment.HomeFragment;

import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolde> {

  private   List<BannerLiveInfo.Live> mliveData;
    private Context mContext;

    public LiveAdapter(List<BannerLiveInfo.Live> pMliveData, Context pContext) {
        mliveData = pMliveData;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolde(LayoutInflater.from(mContext).inflate(R.layout.activity_live_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        BannerLiveInfo.Live live = mliveData.get(position);
        holder.mTextView.setText(live.activityName);
        holder.mTime.setText(live.startTime);
        RequestOptions requestOptions = new RequestOptions().centerInside();
        Glide.with(mContext).load(live.coverImgUrl).apply(requestOptions).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mliveData.size();
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
