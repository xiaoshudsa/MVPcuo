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

import com.zxp.data.Databean;
import com.zxp.mvpcuoqv.R;

import java.util.ArrayList;
import java.util.List;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<Databean.DatasBean> datas=new ArrayList<>();
    private Context mContext;

    public TestAdapter( Context pContext) {
        mContext = pContext;
    }
    public void add(List<Databean.DatasBean> pDatas){
        this.datas.addAll(pDatas);
        notifyDataSetChanged();
    }
    public void clear(){
        this.datas.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Databean.DatasBean pDatas  = datas.get(position);
        holder.title.setText(pDatas.getTitle());
        Glide.with(mContext).load(pDatas.getThumbnail()).into(holder.leftImage);
        holder.desc.setText(pDatas.getDescription());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView leftImage;
        TextView title;
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftImage = itemView.findViewById(R.id.left_image);
            title = itemView.findViewById(R.id.title_content);
            desc = itemView.findViewById(R.id.desc_content);
        }
    }
}
