package kz.nta.kazweather.dagger.modules

import dagger.Module
import dagger.Provides
import kz.nta.kazweather.BuildConfig
import kz.nta.kazweather.dagger.scopes.AppScope
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import android.content.Context
import io.victoralbertos.jolyglot.GsonSpeaker
import io.rx_cache2.internal.RxCache
import kz.nta.kazweather.api.CacheProviders


@Module
class NetworkModule{

    /*@AppScope
    @Provides
    fun interceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader("Accept", "application/json")
            request.addHeader("Cache-Control", "public, max-age=" + 3600)
            if (!Utils.isNetworkConnected(context)) {
            val cacheControl = CacheControl.Builder()
                    .maxStale(1, TimeUnit.HOURS)
                    .build()
            request .cacheControl(cacheControl)
            }
            chain.proceed(request.build())
        }
    }*/
    /* @AppScope
   @Provides
   fun provideHttpCache(context: Context): Cache {
       val cacheSize :Long = 10 * 1024 * 1024
       return Cache(context.cacheDir, cacheSize)
   }*/



    @AppScope
    @Provides
    fun rxCache(context: Context): CacheProviders {
        return RxCache.Builder()
            .setMaxMBPersistenceCache(5)
            .persistence(context.cacheDir, GsonSpeaker())
            .using(CacheProviders::class.java)
    }

    @AppScope
    @Provides
    fun interceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader("Accept", "application/json")
            chain.proceed(request.build())
        }
    }

    @AppScope
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @AppScope
    @Provides
    fun okHttpClient(interceptor: Interceptor, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return if(BuildConfig.DEBUG){
            OkHttpClient().newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(interceptor)
                    .build()
        }else{
            OkHttpClient().newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addNetworkInterceptor(interceptor)
                    .build()
        }
    }
}