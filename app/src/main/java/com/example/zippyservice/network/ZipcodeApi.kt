package com.example.zippyservice.network

import android.util.Log
import com.example.zippyservice.network.ZipcodeResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object ZipcodeApi {
    private const val BASE_URL = "https://api.zippopotam.us"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val logger = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofit = Retrofit.Builder()
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(OkHttpClient.Builder().addInterceptor(logger).build())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: ZipcodeApiService by lazy {
        Log.i("Frank", "Creating the retrofit service...")
        retrofit.create(ZipcodeApiService::class.java)
    }
}

interface ZipcodeApiService {
    @GET("/us/{zipcode}")
    suspend fun getProperties(@Path("zipcode") zipcode:String) : ZipcodeResponse
}