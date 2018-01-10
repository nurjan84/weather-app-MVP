package kz.nta.kazweather.api

import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.LifeCache
import kz.nta.kazweather.mvp.models.CitiesModel
import kz.nta.kazweather.mvp.models.WeatherModel
import java.util.concurrent.TimeUnit

interface CacheProviders {

    @LifeCache(duration = 60, timeUnit = TimeUnit.MINUTES)
    fun getCities(getCitiesObservable: Observable<CitiesModel>, key: DynamicKey): Observable<CitiesModel>
    @LifeCache(duration = 60, timeUnit = TimeUnit.MINUTES)
    fun getWeather(getWeatherObservable: Observable<WeatherModel>, key: DynamicKey): Observable<WeatherModel>
}