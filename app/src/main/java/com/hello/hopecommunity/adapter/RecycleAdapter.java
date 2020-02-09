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
        holder.text_people.setText(list.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView card_image_item;
        public TextView text_people;

        public ViewHolder(View view) {
            super(view);
            card_image_item = view.findViewById(R.id.card_image_item);
            text_people = view.findViewById(R.id.text_people);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
