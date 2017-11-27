package com.example.hasham.movies_mvvm.di.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.hasham.movies_mvvm.BuildConfig
import com.example.hasham.movies_mvvm.data.remote.Repository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Developed by Hasham.Tahir on 1/5/2017.
 */

@Module
class NetModule
@Inject
constructor(private val mBaseUrl: String) {

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    internal fun providesSharedPreferences(application: Application):
            // Application reference must come from AppModule.class
            SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    internal fun providesApiEndpoints(retrofit: Retrofit): Repository.API =
            retrofit.create<Repository.API>(Repository.API::class.java)

    @Provides
    @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.BUILD_TYPE == "debug") {
            httpClient.addInterceptor(logging)
        }
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.cache(cache)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    //  .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

//    @Provides
//    @Singleton
//    internal fun provideMoshi(): Moshi {
//        val builder = Moshi.Builder()
//        return builder.build()
//    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson,/*moshi: Moshi, */okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
//                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addConverterFactory(GsonConverterFactory.create(gson))
                //                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build()
    }
}
