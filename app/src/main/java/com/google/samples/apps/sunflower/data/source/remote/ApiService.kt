/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data.source.remote

import com.google.samples.apps.sunflower.BuildConfig
import com.google.samples.apps.sunflower.MyApplication
import com.google.samples.apps.sunflower.data.source.remote.plants.PlantsApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object Factory{
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
                            }
                            chain.proceed(request)
                        }
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build()
            }

            val mRetrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(mClient)
                    .build()

            return@lazy mRetrofit
        }

        val plantsApiService : PlantsApiService = getApiService.create(PlantsApiService::class.java)
    }
}
