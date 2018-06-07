package com.lrony.iread.presentation.main.online.type.multi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lrony.iread.R;
import com.lrony.iread.presentation.main.online.multi.Type;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Lrony on 18-6-7.
 */
public class BookTypeTitleViewBinder extends ItemViewBinder<Type, BookTypeTitleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.view_book_type_title, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Type item) {
        holder.title.setText(item.title);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_title);
        }
    }
}
