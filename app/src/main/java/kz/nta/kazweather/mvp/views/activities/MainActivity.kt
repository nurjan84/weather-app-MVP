package kz.nta.kazweather.mvp.views.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kz.nta.kazweather.R
import kz.nta.kazweather.mvp.presenters.MainActivityPresenter
import kz.nta.kazweather.mvp.views.interfaces.MainActivityView
import android.widget.Toast
import kz.nta.kazweather.adapters.WeatherListAdapter
import kz.nta.kazweather.mvp.models.WeatherModel
import retrofit2.HttpException

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter lateinit var presenter: MainActivityPresenter
    private lateinit var adapter : WeatherListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar.visibility = View.INVISIBLE
        presenter.getWeather(editText, this)
    }

    override fun onShowLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideLoader() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onWeatherLoaded(resultList : ArrayList<WeatherModel>) {
        if (::adapter.isInitialized){
            adapter.updateList(resultList)
        }else{
            adapter = WeatherListAdapter(this,resultList)
            recyclerView.adapter = adapter
        }
    }

    override fun onErrorWeatherLoading(error: Throwable?) {
        Toast.makeText(this, getString(R.string.error)+" "+error?.message, Toast.LENGTH_SHORT).show()
    }

}
