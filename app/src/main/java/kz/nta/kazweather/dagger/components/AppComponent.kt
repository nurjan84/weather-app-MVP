package kz.nta.kazweather.dagger.components

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import kz.nta.kazweather.api.CacheProviders
import kz.nta.kazweather.dagger.modules.AppModule
import kz.nta.kazweather.dagger.modules.NetworkModule
import kz.nta.kazweather.dagger.scopes.AppScope
import retrofit2.Retrofit

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent{
    fun getRetrofit(): Retrofit
    fun getContext(): Context
    fun getPrefs(): SharedPreferences
    fun getCache(): CacheProviders
}
