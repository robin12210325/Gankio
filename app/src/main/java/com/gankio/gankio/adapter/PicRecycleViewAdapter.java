package com.gankio.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gankio.R;
import com.gankio.gankio.model.AndroidModel;
import com.gankio.gankio.view.PicView;

import java.util.ArrayList;
import java.util.List;

public class PicRecycleViewAdapter extends RecyclerView.Adapter<PicRecycleViewAdapter.PicViewHolder>{
    private Context mContext;
    private List<AndroidModel.ResultsBean> list = new ArrayList<>();
    PicRecyAdapter picRecyAdapter;
    LinearLayoutManager linearLayoutManager;

    private LayoutInflater layoutInflater;
    public PicRecycleViewAdapter(Context mContext){
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }
    public void setLists(List<AndroidModel.ResultsBean> datas){
        list.clear();
        this.list.addAll(datas);
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.pic_gankio,parent,false);
        return new PicRecycleViewAdapter.PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicViewHolder holder, int position) {
        System.out.println("onBindViewHolder=" + list.get(position).getUrl());
        Glide.with(mContext).load(list.get(position).getUrl()).into(holder.view);
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener.onItemClick(v,position);
//            }
//        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PicView.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl",list.get(position).getUrl());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PicViewHolder extends RecyclerView.ViewHolder{
        ImageView view;
        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            view = (ImageView) itemView.findViewById(R.id.pic_image);
        }
    }
    public PicRecycleViewAdapter.OnItemClickListener itemClickListener;
    public  void setOnItemClickListener(PicRecycleViewAdapter.OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
