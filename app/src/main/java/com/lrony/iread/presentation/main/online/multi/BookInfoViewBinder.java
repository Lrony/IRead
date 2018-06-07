package com.lrony.iread.presentation.main.online.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.ui.widget.ShapeTextView;
import com.lrony.iread.util.ImageLoader;
import com.lrony.iread.util.KLog;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Lrony on 18-5-23.
 */
public class BookInfoViewBinder extends ItemViewBinder<BookInfo, BookInfoViewBinder.ViewHolder> {

    private static final String TAG = "BookInfoViewBinder";

    private static Context context;

    public BookInfoViewBinder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.view_online_item_book, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BookInfo item) {
        holder.bookInfo = item;
        holder.title.setText(item.title);
        holder.author.setText(item.author);
        holder.shortIntro.setText(item.shortIntro);
        holder.majorCate.setText(item.majorCate);
        holder.retentionRatio.setText(item.retentionRatio + "");

        ImageLoader.load(context, item.cover, holder.cover);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private BookInfo bookInfo;

        @NonNull
        private final ImageView cover;
        @NonNull
        private final TextView title;
        @NonNull
        private final TextView author;
        @NonNull
        private final TextView shortIntro;
        @NonNull
        private final ShapeTextView majorCate;
        @NonNull
        private final ShapeTextView retentionRatio;


        public ViewHolder(View itemView) {
            super(itemView);
            this.cover = itemView.findViewById(R.id.iv_cover);
            this.author = itemView.findViewById(R.id.tv_author);
            this.title = itemView.findViewById(R.id.tv_title);
            this.shortIntro = itemView.findViewById(R.id.tv_describe);
            this.majorCate = itemView.findViewById(R.id.tv_type);
            this.retentionRatio = itemView.findViewById(R.id.tv_retention_ratio);

            itemView.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    if (bookInfo != null) {
                        String bookid = bookInfo.id;
                        KLog.d(TAG, "bookid: " + bookid);
                        AppRouter.showBookDetailActivity(context, bookid);
                    } else {
                        KLog.d(TAG, "bookInfo is null !!!");
                    }
                }
            });
        }
    }
}
