package com.gankio.utils;

import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by gaoyongxing on 2018-11-20.
 */
public class RetrofitUtil {
    private static final String TAG = "retrofit";
    //TODO:修改主机地址
    private static final String BASE_URL = "http://gank.io/api/";
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit;
    public static ServiceApi serviceApi;
    //实例化私有
    private RetrofitUtil(){

    }
    //单例Retrofit
    public static ServiceApi getInstance(){
        if (retrofit == null) {
            synchronized(RetrofitUtil.class){
                if (retrofit == null) {
                    //这里有个很关键的地方，就是传入App的上下文啦。
                    ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();                //okhttp创建，写入缓存机制，还有addInterceptor
                    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                    File cacheFile = new File(App.getInstance().getCacheDir(), "superman");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb。这里开始就是缓存设置啦。实现一键缓存以及请求头的配置等等
                    httpClientBuilder.cache(cache);
                    httpClientBuilder.cookieJar(cookieJar);
                    httpClientBuilder.addInterceptor(LoggingInterceptor);
//                    httpClientBuilder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
//                    httpClientBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);

                    retrofit =  new Retrofit.Builder()                          //retrofit的创建。
                            .client(httpClientBuilder.build())          //传入okhttp
                            .addConverterFactory(GsonConverterFactory.create())             //传入gson解析手段
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())           //传入异步手段
                            .baseUrl(BASE_URL)              //传入服务器地址
                            .build();
                }
            }
        }
        if (null != retrofit){
            serviceApi = retrofit.create(ServiceApi.class);
        }
        return serviceApi;
    }
    //okHttp的拦截器
    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.d("LogInterceptor", "okhttp3:" + request.toString());//输出请求前整个url
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
//          Log.v(TAG,response.request().url()+response.headers());//输出一个请求的网络信息
            Log.d("LogInterceptor:",(t2-t1)/1000000 + "毫秒");
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.d("LogInterceptor", "response body:" + content);//输出返回信息
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    };
    //okHttp的拦截器
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetworkUtils.isConnected(App.getInstance())){//判断是否有网络再进行操作
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            //请求头缓存控制
            Response originalResponse = chain.proceed(request);
            if(NetworkUtils.isConnected(App.getInstance())){
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}
