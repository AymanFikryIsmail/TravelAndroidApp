package com.travel.iti.travelapp.repository.networkmodule;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;

/**
 * Created by ayman on 2019-05-13.
 */

public class NetworkManager {
    protected static Retrofit retrofit;
    public static String BASE_URL= "http://192.168.43.104:3000";//"https://wetravelapp.herokuapp.com/";//10.1.41.161 172.16.6.194

    private ApiRequest apiRequest;
    private static NetworkManager networkManager;
    private NetworkManager() { }
    public   static NetworkManager getInstance() {
        if (networkManager == null) {
            synchronized (NetworkManager.class) {// class level synchronization
                if (networkManager == null) {
                    networkManager = new NetworkManager();
                }
            }
        }
        return networkManager;
    }
        public  ApiRequest createService(final String githubToken) {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);

        if (!TextUtils.isEmpty(githubToken)) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newReq = request.newBuilder()
                            .addHeader("Authorization", format("token %s", githubToken))
                            .build();
                    return chain.proceed(newReq);
                }
            }).build();//.addInterceptor(interceptor)

            builder.client(client);
        }

        return builder.build().create(ApiRequest.class);
    }
}
