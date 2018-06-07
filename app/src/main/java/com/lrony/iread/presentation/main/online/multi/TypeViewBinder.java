package com.lrony.iread.presentation.main.online.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.ui.help.OnMultiClickListener;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Lrony on 18-5-23.
 */
public class TypeViewBinder extends ItemViewBinder<Type, TypeViewBinder.ViewHolder> {

    private static Context context;

    public TypeViewBinder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.view_online_item_type, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Type item) {
        holder.type = item;
        holder.title.setText(item.title);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Type type;

        @NonNull
        private final TextView title;
        @NonNull
        private final RelativeLayout more;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_title);
            this.more = itemView.findViewById(R.id.rl_recommend_more);

            more.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    AppRouter.showOnlineMoreActivity(context, type.title);
                }
            });
        }
    }
}
