package com.lrony.iread.presentation.main.local;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.lrony.iread.R;
import com.lrony.iread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-5-22.
 */
public class LocalFragment extends MvpFragment<LocalContract.Presenter> implements LocalContract.View {

    private static final String TAG = "LocalFragment";

    public static LocalFragment newInstance() {
        LocalFragment fragment = new LocalFragment();
        return fragment;
    }

    @NonNull
    @Override
    public LocalContract.Presenter createPresenter() {
        return new LocalPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_local;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().start();
    }
}
