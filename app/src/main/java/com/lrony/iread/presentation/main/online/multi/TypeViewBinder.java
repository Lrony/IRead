package com.lrony.iread.presentation.main.online.multi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lrony.iread.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Lrony on 18-5-23.
 */
public class TypeViewBinder extends ItemViewBinder<Type, TypeViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.view_online_item_type, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Type item) {
        holder.title.setText(item.title);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView title;
        @NonNull
        private final RelativeLayout more;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_title);
            this.more = itemView.findViewById(R.id.rl_recommend_more);

            more.setOnClickListener(v -> {

            });
        }
    }
}
