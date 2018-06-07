package com.lrony.iread.presentation.main.online.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.ui.help.OnMultiClickListener;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Lrony on 18-6-7.
 */
public class OpenMoreViewBinder extends ItemViewBinder<More, OpenMoreViewBinder.ViewHolder> {

    private static Context context;

    public OpenMoreViewBinder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.view_open_more, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull More item) {
        holder.more = item;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private More more;

        @NonNull
        private final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.tv_open_more);

            title.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    AppRouter.showOnlineMoreActivity(context, more.title);
                }
            });
        }
    }
}
