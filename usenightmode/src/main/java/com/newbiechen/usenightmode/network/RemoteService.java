package com.newbiechen.usenightmode.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PC on 2016/11/3.
 * 利用OkHttp封装简单的网络请求
 */

public class RemoteService {
    private static final int TIME_OUT = 5;

    private static RemoteService sRemoteService;
    private final OkHttpClient mClient;
    //让Handler一直位于主线程
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public RemoteService() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    /**
     * 单例模式
     * @return
     */
    public static RemoteService getInstance(){
        synchronized (RemoteService.class){
            if (sRemoteService == null){
                sRemoteService = new RemoteService();
            }
        }
        return sRemoteService;
    }

    public <T> void connHttp(String url, final RemoteCallback<T> callBack){
        //Get请求
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call connCall = mClient.newCall(request);
        connCall.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                //切换执行写成
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null){
                            callBack.onFailure();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack != null){
                    final T data = callBack.parse(response);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onResponse(data);
                        }
                    });
                }
            }
        });
    }

    public interface RemoteCallback<T>{
        T parse(Response response);

        void onFailure();

        void onResponse(T data);
    }
}
