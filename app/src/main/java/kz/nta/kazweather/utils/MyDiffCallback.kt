package kz.nta.kazweather.utils


import android.support.v7.util.DiffUtil
import kz.nta.kazweather.mvp.models.WeatherModel

class MyDiffCallback(private var weatherListNew: ArrayList<WeatherModel>, private var weatherListOld: ArrayList<WeatherModel>) :
        DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return weatherListOld[oldItemPosition].cod == weatherListNew[newItemPosition].cod
    }

    override fun getOldListSize(): Int {
       return weatherListOld.size
    }

    override fun getNewListSize(): Int {
        return weatherListNew.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return weatherListOld[oldItemPosition].main == weatherListNew[newItemPosition].main
    }

}
