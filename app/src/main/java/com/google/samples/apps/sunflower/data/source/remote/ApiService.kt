package com.google.samples.apps.sunflower.data.source.remote

import com.google.samples.apps.sunflower.BuildConfig
import com.google.samples.apps.sunflower.MyApplication
import com.google.samples.apps.sunflower.data.source.remote.plants.PlantsApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object Factory {
        val getApiService : Retrofit by lazy {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val cacheSize = (5 * 1024 * 1024).toLong()
            val appCache = Cache(MyApplication.getContext().cacheDir, cacheSize)
            val mClient = if (BuildConfig.DEBUG) {
                OkHttpClient.Builder()
                        .cache(appCache)
                        .addInterceptor { chain ->
                            val request = chain.request().apply {
                                newBuilder().header("Cache-Control",
                                        "public, max-age=" + 5).build()

                                //query parameter is used to put param to every request
                                //just like @Query in a single request
                                val url = url.newBuilder()
                                        .addQueryParameter("api_key", "put_an_api_key_here").build()
                                newBuilder().url(url).build()
                            }
                            chain.proceed(request)
                        }
                        .addInterceptor(mLoggingInterceptor)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build()
            } else {
                OkHttpClient.Builder()
                        .cache(appCache)
                        .addInterceptor { chain ->
                            val request = chain.request().apply {
                                newBuilder().header("Cache-Control",
                                        "public, max-age=" + 5).build()

                                //query parameter is used to put param to every request
                                //just like @Query in a single request
                                val url = url.newBuilder()
                                        .addQueryParameter("api_key", "put_an_api_key_here").build()
                                newBuilder().url(url).build()
                            }
                            chain.proceed(request)
                        }
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build()
            }

            val mRetrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(mClient)
                    .build()

            return@lazy mRetrofit
        }

        val plantsApiService : PlantsApiService = getApiService.create(PlantsApiService::class.java)
    }
}
