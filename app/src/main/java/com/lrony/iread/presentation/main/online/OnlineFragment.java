package com.lrony.iread.presentation.main.online;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.lrony.iread.R;
import com.lrony.iread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-5-22.
 */
public class OnlineFragment extends MvpFragment<OnlineContract.Presenter> implements OnlineContract.View {

    private static final String TAG = "OnlineFragment";

    public static OnlineFragment newInstance() {
        OnlineFragment fragment = new OnlineFragment();
        return fragment;
    }

    @NonNull
    @Override
    public OnlineContract.Presenter createPresenter() {
        return new OnlinePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_online;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().start();
    }
}
