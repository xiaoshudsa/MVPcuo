package com.zxp.mvpcuoqv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zxp.data.BannerLiveInfo;
import com.zxp.data.IndexCommondEntity;
import com.zxp.mvpcuoqv.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter {
    private List<IndexCommondEntity> bottomList;
    private List<String> bannerData;
    private List<BannerLiveInfo.Live> liveData=new ArrayList<>();
    private Context mContext;

    public HomeAdapter(List<IndexCommondEntity> pBottomList, List<String> pBannerData , Context pContext) {
        this.bottomList = pBottomList;
        this.bannerData = pBannerData;

        this.mContext = pContext;
    }
    public void addLiveData(List<BannerLiveInfo.Live> pLiveData){
        this.liveData.addAll(pLiveData);
        notifyDataSetChanged();
    }
    public void clearLiveData(List<BannerLiveInfo.Live> pLiveData){
        this.liveData.clear();
        this.liveData.addAll(pLiveData);
        notifyDataSetChanged();
    }
    private final int BANNER = 1, LABEL = 2, LIVE = 3, RIGHT_IMAGE = 4, BIG_IMAGE = 5;
    @Override
    public int getItemViewType(int position) {
        int type = RIGHT_IMAGE;
        if (position == 0) type = BANNER;
        else if (position == 1) type = LABEL;
        else if (liveData != null && liveData.size() != 0 && position == 2) type = LIVE;
        else {
            int usePos = liveData != null && liveData.size() != 0 ? position - 3 : position - 2;
            if (bottomList != null && bottomList.size() != 0) {
                if (bottomList.get(usePos).type == 3) {
                    type = BIG_IMAGE;
                } else type = RIGHT_IMAGE;
            }
        }

        return type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case BANNER:
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.activity_ling, parent, false);
                return new BannerView(inflate);
            case LABEL:
                View inflate1 = LayoutInflater.from(mContext).inflate(R.layout.activity_yi, parent, false);
                return new Label(inflate1);
            case LIVE:
                View inflate4 = LayoutInflater.from(mContext).inflate(R.layout.activity_live, parent, false);
                return new Live(inflate4);
            case RIGHT_IMAGE:
                View inflate2 = LayoutInflater.from(mContext).inflate(R.layout.activity_san, parent, false);
                return new Right(inflate2);
            case BIG_IMAGE:
                View inflate3 = LayoutInflater.from(mContext).inflate(R.layout.activity_si, parent, false);
                return new Big(inflate3);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case BANNER:
                BannerView holder1 = (BannerView) holder;
                    holder1.banner.setImages(bannerData).setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            String path1 = (String) path;
                            Glide.with(context).load(path1).into(imageView);
                        }
                    }).start();

                break;
            case   LABEL:
                break;
            case LIVE:
                Live holder4 = (Live) holder;
                if (liveData!=null&&liveData.size()>0) {
                    LiveAdapter liveAdapter = new LiveAdapter(liveData, mContext);
                    holder4.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                    holder4.mRecyclerView.setAdapter(liveAdapter);
                }
                break;
            case RIGHT_IMAGE:
                Right holder2 = (Right) holder;
                if (liveData != null && liveData.size() != 0) {position=position-3;}
                else {position=position-2;}
                if (bottomList!=null&&bottomList.size()!=0) {
                    IndexCommondEntity indexCommondEntity = bottomList.get(position);
                    holder2.mTextView.setText(indexCommondEntity.title);
                    RequestOptions requestOptions = new RequestOptions().bitmapTransform(new RoundedCorners(50));
                    Glide.with(mContext).load(indexCommondEntity.pic).apply(requestOptions).into(holder2.mImageView);
                }
                break;
            case BIG_IMAGE:
                Big holder3 = (Big) holder;
                if (liveData != null && liveData.size() != 0) {position=position-3;}
                else {position=position-2;}
                if (bottomList!=null&&bottomList.size()!=0) {
                    IndexCommondEntity indexCommondEntityBig = bottomList.get(position);
                    holder3.time.setText(indexCommondEntityBig.date);
                    holder3.textView.setText(indexCommondEntityBig.title);
                    RequestOptions requestOptionsBig = new RequestOptions().bitmapTransform(new RoundedCorners(50));
                    Glide.with(mContext).load(indexCommondEntityBig.pic).apply(requestOptionsBig).into(holder3.imageView);
                }
                break;
        }
    }





    @Override
    public int getItemCount() {
        return liveData !=null && liveData.size() != 0 ? bottomList.size()+3 : bottomList.size()+2;
    }
    class Live extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        public Live(@NonNull View itemView) {
            super(itemView);
            mRecyclerView= itemView.findViewById(R.id.rect_v);
        }
    }

    class BannerView extends RecyclerView.ViewHolder {
        Banner banner;

        public BannerView(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }

    class Label extends RecyclerView.ViewHolder {

        public Label(@NonNull View itemView) {
            super(itemView);

        }
    }


    class Right extends RecyclerView.ViewHolder {
            TextView mTextView;
            ImageView mImageView;
        public Right(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_title);
            mImageView= itemView.findViewById(R.id.iv_img);
        }
    }

    class Big extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;
            TextView  time;
        public Big(@NonNull View itemView) {
            super(itemView);
            time= itemView.findViewById(R.id.te_time);
            imageView= itemView.findViewById(R.id.iv_img);
            textView= itemView.findViewById(R.id.tv_wen);
        }
    }
}
