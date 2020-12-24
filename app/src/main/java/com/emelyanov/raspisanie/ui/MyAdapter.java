package com.emelyanov.raspisanie.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.emelyanov.raspisanie.R;
import com.emelyanov.raspisanie.ui.edit.EditFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder>{

    static boolean isEdit;

    List<Item> items;
    EditFragment fragment;
    public MyAdapter(List<Item> items) {
        this.items = items;
        isEdit = false;
    }

    public MyAdapter(List<Item> items, EditFragment fragment) {
        this.items = items;
        this.fragment = fragment;

        isEdit = true;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).name);
        holder.itemTime.setText(items.get(position).age);

        if (isEdit) {
            holder.cv.setOnClickListener(l -> {
                fragment.edit(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView itemName;
        TextView itemTime;
        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            itemTime = (TextView)itemView.findViewById(R.id.item_time);
        }
    }
}