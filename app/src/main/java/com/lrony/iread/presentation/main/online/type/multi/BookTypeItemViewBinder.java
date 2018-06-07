package com.lrony.iread.presentation.main.online.type.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lrony.iread.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Lrony on 18-6-7.
 */
public class BookTypeItemViewBinder extends ItemViewBinder<BookTypeItem, BookTypeItemViewBinder.ViewHolder> {

    private Context context;

    public BookTypeItemViewBinder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.view_book_type_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BookTypeItem item) {
        holder.title.setText(item.title);
        holder.num.setText(context.getResources().getString(R.string.book_type_count, item.num));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView title;
        @NonNull
        private final TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_title);
            this.num = itemView.findViewById(R.id.tv_count);
        }
    }
}
