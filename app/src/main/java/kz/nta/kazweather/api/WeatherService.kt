package kz.nta.kazweather.api

import io.reactivex.Observable
import kz.nta.kazweather.mvp.models.CitiesModel
import kz.nta.kazweather.mvp.models.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherService {
    companion object {
        val weatherBaseUrl = "http://api.openweathermap.org/data/2.5/weather"
        val baseUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/"
    }

    @GET("json")
    fun getCities(@Query("input")input:String,
                  @Query("types")types:String,
                  @Query("key")key:String
                    ): Observable<CitiesModel>

    @GET
    fun getWeather(@Url url:String, @Query("q")city:String,@Query("appid")appid:String ): Observable<WeatherModel>
}