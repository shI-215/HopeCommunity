package com.hello.hopecommunity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hello.hopecommunity.App;
import com.hello.hopecommunity.R;

import java.util.List;
import java.util.Map;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<Map<String, Object>> list;
    private Context context;

    public RecycleAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        String url = App.HOPE_URL + list.get(position).get("image").toString();
        Log.v("URL------->>>>>>", url);
        Glide.with(context)
                .load(url)
                .thumbnail(0.2f)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_failed)
                .into(holder.card_image_item);
        holder.card_image_item.setTag(position);
        holder.text_title_item.setText(list.get(position).get("title").toString());
        Log.v("text_title_item------->", list.get(position).get("title").toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView card_image_item;
        public TextView text_title_item;

        public ViewHolder(View view) {
            super(view);
            card_image_item = view.findViewById(R.id.card_image_item);
            card_image_item.setOnClickListener(this);
            text_title_item = view.findViewById(R.id.text_title_item);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (mOnItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.recycler_view:
                        mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                        break;
                    default:
                        mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                        break;
                }
            }
        }
    }

    //item里面有多个控件可以点击
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);

        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
