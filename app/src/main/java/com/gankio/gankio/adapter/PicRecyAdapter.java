package com.gankio.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gankio.R;
import com.gankio.gankio.view.PicView;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Created by gaoyongxing on 2018-3-30.
 */
public class PicRecyAdapter extends RecyclerView.Adapter<PicRecyAdapter.PicViewHolder> {
    Context mContext;
    private List<String> datas;
    String orderNum;

    public PicRecyAdapter(Context mContext, List<String> images) {
        this.mContext = mContext;
        this.datas = images;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pic_item, parent, false);

        final PicViewHolder holder = new PicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {

        Glide.with(mContext).load(datas.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PicView.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl",datas.get(position));
                intent.putExtras(bundle);
                System.out.println("urlqqq=" + datas.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null?0:datas.size();
    }

    static class PicViewHolder extends RecyclerView.ViewHolder {

        PhotoView imageView;

        public PicViewHolder(View itemView) {
            super(itemView);
            imageView = (PhotoView) itemView.findViewById(R.id.android_pic);
        }
    }
}
