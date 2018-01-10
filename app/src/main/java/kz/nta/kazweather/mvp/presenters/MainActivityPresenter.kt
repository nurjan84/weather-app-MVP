package kz.nta.kazweather.mvp.presenters

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import com.arellomobile.mvp.InjectViewState
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.DynamicKey
import kz.nta.kazweather.R
import kz.nta.kazweather.WeatherApplication
import kz.nta.kazweather.api.CacheProviders
import kz.nta.kazweather.api.WeatherService
import kz.nta.kazweather.dagger.components.DaggerServiceComponent
import kz.nta.kazweather.mvp.models.WeatherModel
import kz.nta.kazweather.mvp.views.interfaces.MainActivityView
import kz.nta.kazweather.utils.Logger
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@InjectViewState
class MainActivityPresenter @Inject constructor() : BasePresenter<MainActivityView>(){
    init {
        DaggerServiceComponent.builder()
                .appComponent(WeatherApplication.getApplicationComponent())
                .build().injectMainActivityPresenter(this)
    }
    @Inject lateinit var apiService: WeatherService
    @Inject lateinit var context: Context
    @Inject lateinit var prefs: SharedPreferences
    @Inject lateinit var cache: CacheProviders

    fun getWeather(input:EditText, activity:Activity){

        val placesKey = context.getString(R.string.places_api_key)
        val type = "(cities)"
        val weatherKey = context.getString(R.string.weather_api_key)


        // загружаем последний искомый тест, если есть
        input.setText(getLastInput())

        // добавляем disposable, кторый получается в результате подписки на изменения текстового поля,
        // чтобы потом отчистить, при уничтожении активити
        disposables.add(
                // создаем Observable, эмиссии которого, мы будем слушать
                RxTextView.afterTextChangeEvents(input)
                        // слушаем эмисси каждые 300 милисекунд
                        .debounce(300,TimeUnit.MILLISECONDS)
                        // конвертируем в стринг, так как далее в параметры запроса нужен строковый тип данных
                        .map {it.editable().toString() }
                        // идем дальше, только если в поле было введено 2 и более символов
                        .filter {it.length>=2}
                        // запускаем в активити индикатор загрузки данных
                        .doOnNext {
                            activity.runOnUiThread({viewState.onShowLoader()})
                            // сохраняем тект запроса, чтобы, при холодном старте, загрузить кэшированные данные
                            saveLastRequest(it)
                        }
                        // используем switchMap, так как оно автоматически отменяет предыдущие подписки
                        // и создает новые, недожидаясь ответа от сервера.
                        // Посылаем запрос на получение списка городов по введенным буквам
                        .switchMap {
                            it ->
                            cache.getCities(apiService.getCities(it, type, placesKey), DynamicKey(it))
                                .doOnError { activity.runOnUiThread({
                                    Logger.i("doOnError")
                                    viewState.onHideLoader()
                                })}
                                .onErrorResumeNext(Observable.empty())
                        }
                        // убираем одинаковые города
                        .flatMap { it -> val setOfCities = HashSet<String>()
                            it.predictions.mapTo(setOfCities) { it.structured_formatting.main_text }
                            Observable.fromArray(setOfCities)
                        }
                        // используя названия городов, полученные из предыдущего запроса, посылаем
                        // N  запросов для получения данных о погоде в этих городах
                        .switchMap {
                            // создаем список запросов размером равный количесву найденных городов
                            it-> val listOfObservables = ArrayList<Observable<WeatherModel>>()
                            it.mapTo(listOfObservables) {
                                cache.getWeather(apiService.getWeather(WeatherService.weatherBaseUrl, it, weatherKey), DynamicKey(it))
                            }
                            // делаем один обзервер, который делает N запросов на погоду
                            Observable.zip(listOfObservables, {r -> r})
                                    .doOnError { activity.runOnUiThread({
                                        viewState.onHideLoader()
                                    })}
                                    .onErrorResumeNext(Observable.empty())
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        // показываем результаты
                        .subscribe(
                                { result -> run {
                                    val results = ArrayList<WeatherModel>()
                                    result.mapTo(results) { it as WeatherModel}
                                    viewState.onWeatherLoaded(results)
                                    viewState.onHideLoader()
                                }},
                                { error -> run{
                                    viewState.onErrorWeatherLoading(error)
                                    viewState.onHideLoader()
                                }}
                        )
        )
    }

    private fun saveLastRequest(input:String){
        prefs.edit().putString("last_input", input).apply()
    }

    private fun getLastInput():String{
        return prefs.getString("last_input", "")
    }
}