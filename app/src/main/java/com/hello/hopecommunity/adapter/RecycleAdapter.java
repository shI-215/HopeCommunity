package com.hello.hopecommunity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hello.hopecommunity.R;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<String> list;

    public RecycleAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        holder.card_image_item.setImageResource(R.drawable.background);
        holder.card_image_item.setTag(position);
        holder.text_people.setText(list.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView card_image_item;
        public TextView text_people;

        public ViewHolder(View view) {
            super(view);
            card_image_item = view.findViewById(R.id.card_image_item);
            card_image_item.setOnClickListener(this);
            text_people = view.findViewById(R.id.text_people);
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
