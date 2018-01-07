package kz.nta.kazweather.mvp.views.interfaces

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.nta.kazweather.mvp.models.WeatherModel

@StateStrategyType(OneExecutionStateStrategy ::class)
interface MainActivityView:MvpView{
    fun onShowLoader()
    fun onHideLoader()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onWeatherLoaded(resultList : ArrayList<WeatherModel>)
    fun onErrorWeatherLoading(error:Throwable?)
}