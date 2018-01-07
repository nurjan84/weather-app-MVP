package kz.nta.kazweather.dagger.modules

import dagger.Module
import dagger.Provides
import kz.nta.kazweather.api.WeatherService
import kz.nta.kazweather.dagger.scopes.PerActivity
import retrofit2.Retrofit

@Module
class ServiceModule {

    @PerActivity
    @Provides
    fun  getApiService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

}
