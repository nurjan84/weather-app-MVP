package kz.nta.kazweather.adapters

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_city.view.*
import kz.nta.kazweather.R
import kz.nta.kazweather.mvp.models.WeatherModel
import kz.nta.kazweather.utils.MyDiffCallback

class WeatherListAdapter internal constructor(private val context: Context, private val weatherList: ArrayList<WeatherModel>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false)
        return Holder(view)
    }
    override fun getItemViewType(position: Int): Int = position
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as Holder
        holder.bindViews()
    }
    override fun getItemCount(): Int = weatherList.size

    fun updateList(newWeatherList: ArrayList<WeatherModel>){
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(newWeatherList,this.weatherList))
        this.weatherList.clear()
        this.weatherList.addAll(newWeatherList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(){
            itemView.city.text = weatherList[adapterPosition].name
            val temp = weatherList[adapterPosition].main.temp - 273.15
            itemView.temperature.text =  String.format("%.1f C", temp)
        }
    }
}
