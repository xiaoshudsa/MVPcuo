package com.zxp.mvpcuoqv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yiyatech.utils.ext.JSONUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zxp.data.VipListBean;
import com.zxp.data.VipLiveBean;
import com.zxp.mvpcuoqv.R;

import java.util.List;



public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {
    private static final int BANNER = 0;
    private static final int VIPLIVE = 1;
    private static final int LIST = 2;
    private List<VipLiveBean.LiveBeanX.LiveBean> mLiveBeans;
    private List<VipLiveBean.LunbotuBean> mLunbotuBeanList;
    private List<VipListBean.ListBean> mListBeans;
    private Context mContext;

    public VipAdapter(List<VipLiveBean.LiveBeanX.LiveBean> pLiveBeans, List<VipLiveBean.LunbotuBean> pLunbotuBeanList, List<VipListBean.ListBean> pListBeans, Context pContext) {
        mLiveBeans = pLiveBeans;
        mLunbotuBeanList = pLunbotuBeanList;
        mListBeans = pListBeans;
        mContext = pContext;
    }
   int  layoutId;
    @Override
    public int getItemViewType(int position) {
        int type;
        if (position==0){
            type= BANNER;
        }else if (position==1){
            type= VIPLIVE;
        }else {
            type= LIST;
        }
        return type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case BANNER:
                layoutId=R.layout.activity_ling;
                break;
            case VIPLIVE:
                layoutId=R.layout.activity_live;
                break;
            case LIST:
                layoutId=R.layout.activity_grid;
                break;
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(layoutId, parent, false),viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case BANNER:
                if (mLunbotuBeanList.size()>0&&mLunbotuBeanList!=null) {
                    holder.banner.setImages(mLunbotuBeanList).setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            VipLiveBean.LunbotuBean path1 = (VipLiveBean.LunbotuBean) path;
                            Glide.with(context).load(path1.getImg()).into(imageView);
                        }
                    }).start();
                }
                break;
            case VIPLIVE:
                if (mLiveBeans!=null&&mLiveBeans.size()>0) {
                    VipLiveAdapter liveAdapter = new VipLiveAdapter(mLiveBeans, mContext);
                    holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                    holder.mRecyclerView.setAdapter(liveAdapter);
                }
                break;
            case LIST:
                if (mListBeans!=null&&mListBeans.size()>0) {
                    VipGirdeAdapter vipGirdeAdapter = new VipGirdeAdapter(mListBeans, mContext);
                    holder.mRecyclerViewgrid.setLayoutManager(new GridLayoutManager(mContext, 2));
                    holder.mRecyclerViewgrid.setAdapter(vipGirdeAdapter);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return  mLiveBeans!=null&&mLunbotuBeanList!=null?3:2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        Banner banner;
        RecyclerView mRecyclerViewgrid;
        public ViewHolder(@NonNull View itemView, int pViewType) {
            super(itemView);
            switch (pViewType){
                case BANNER:
                    banner = itemView.findViewById(R.id.banner);
                    break;
                case VIPLIVE:
                    mRecyclerView= itemView.findViewById(R.id.rect_v);
                    break;
                case LIST:
                    mRecyclerViewgrid= itemView.findViewById(R.id.recyclerView);
                    break;
            }

        }
    }
}
