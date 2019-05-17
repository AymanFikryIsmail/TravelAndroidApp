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
    private ApiRequest apiRequest;
    private static NetworkManager networkManager;
    private NetworkManager() { }
    public synchronized static NetworkManager getInstance() {
        if (networkManager == null) {
            if (networkManager == null) {
                networkManager = new NetworkManager();
            }
        }
        return networkManager;
    }
        public  ApiRequest createGithubService(final String githubToken) {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.16.5.216:3000/");
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
