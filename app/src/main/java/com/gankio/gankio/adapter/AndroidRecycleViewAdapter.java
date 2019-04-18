package com.gankio.gankio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gankio.R;
import com.gankio.base.MyRecycleView;
import com.gankio.gankio.model.AndroidModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gaoyongxing on 2018-11-22.
 */
public class AndroidRecycleViewAdapter extends RecyclerView.Adapter<AndroidRecycleViewAdapter.ViewHolder>{
    private Context mContext;
    private List<AndroidModel.ResultsBean> list = new ArrayList<>();
    PicRecyAdapter picRecyAdapter;
    LinearLayoutManager linearLayoutManager;

    private LayoutInflater layoutInflater;
    public AndroidRecycleViewAdapter(Context mContext){
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }
    public void setLists(List<AndroidModel.ResultsBean> datas){
        list.clear();
        this.list.addAll(datas);
        System.out.println("androidFragentData=" + list.size() + "//" + String.valueOf(list));
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.layout_android_fragment_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.androidPublishedAt.setText(list.get(i).getPublishedAt().substring(0,10));
        viewHolder.androidDesc.setText(list.get(i).getDesc());
        viewHolder.androidUrl.setText(list.get(i).getUrl());
        viewHolder.androidUrl.setVisibility(View.GONE);
        viewHolder.androidAuthorName.setText(list.get(i).getWho());
        if (list.get(i).getImages() != null && list.get(i).getImages().size()>0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                }
            };
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.myRecycleView.setLayoutManager(layoutManager);

            picRecyAdapter = new PicRecyAdapter(mContext, list.get(i).getImages());
            viewHolder.myRecycleView.setAdapter(picRecyAdapter);
        }else{
            viewHolder.myRecycleView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView androidPublishedAt,androidDesc,androidUrl,androidAuthorName;
        private MyRecycleView myRecycleView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            androidPublishedAt = (TextView) itemView.findViewById(R.id.androidPublishedAt);
            androidDesc = (TextView) itemView.findViewById(R.id.androidDesc);
            androidUrl = (TextView) itemView.findViewById(R.id.androidUrl);
            myRecycleView = (MyRecycleView) itemView.findViewById(R.id.androidPicMyRecycleView);
            androidAuthorName = (TextView) itemView.findViewById(R.id.androidAuthorName);

        }
    }
    public OnItemClickListener itemClickListener;
    public  void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
