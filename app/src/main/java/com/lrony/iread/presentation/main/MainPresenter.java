package com.lrony.iread.presentation.main;

import com.lrony.iread.model.bean.WYHotBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.model.remote.OtherApi;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.pref.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-5-22.
 */
public class MainPresenter extends MvpBasePresenter<MainContract.View> implements MainContract.Presenter {

    private OtherApi otherApi;

    @Override
    public void start() {
        super.start();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_WY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        otherApi = retrofit.create(OtherApi.class);
    }

    @Override
    public void loadWYHotData() {
        if (otherApi == null) return;

        Call<WYHotBean> call = otherApi.getWYHotData();
        call.enqueue(new Callback<WYHotBean>() {
            @Override
            public void onResponse(Call<WYHotBean> call, Response<WYHotBean> response) {
                List<WYHotBean> beans = DBManger.getInstance().loadWYHotData();
                if (beans != null) {
                    // 默认保存十条数据
                    if (beans.size() >= 10) DBManger.getInstance().clearWYHotData();
                }
                DBManger.getInstance().saveWYHotData(response.body());
            }

            @Override
            public void onFailure(Call<WYHotBean> call, Throwable t) {

            }
        });
    }
}
