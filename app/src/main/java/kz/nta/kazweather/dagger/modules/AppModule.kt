package kz.nta.kazweather.dagger.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import kz.nta.kazweather.dagger.scopes.AppScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule{

    lateinit var context: Context
    lateinit var baseUrl:String

    constructor(context: Context, baseUrl: String) {
        this.context = context
        this.baseUrl = baseUrl
    }

    @AppScope
    @Provides
    fun context(): Context {
        return context
    }


    @AppScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient, provideRxAdapter: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(provideRxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
    }

    @AppScope
    @Provides
    fun provideRxAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @AppScope
    @Provides
    fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }
}
