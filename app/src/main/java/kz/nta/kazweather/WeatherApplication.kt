package kz.nta.kazweather

import android.app.Application
import kz.nta.kazweather.api.WeatherService
import kz.nta.kazweather.dagger.components.AppComponent
import kz.nta.kazweather.dagger.components.DaggerAppComponent
import kz.nta.kazweather.dagger.modules.AppModule

class WeatherApplication : Application(){

    companion object {
        lateinit var appComponent: AppComponent
        fun getApplicationComponent(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    private fun createAppComponent(){
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this, WeatherService.baseUrl))
                .build()
    }

}
