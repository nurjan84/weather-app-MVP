package kz.nta.kazweather.mvp.models



data class WeatherModel(
		val coord: Coord,
		val weather: List<Weather>,
		val base: String, //stations
		val main: Main,
		val visibility: Int, //1000
		val wind: Wind,
		val clouds: Clouds,
		val dt: Int, //1515241800
		val sys: Sys,
		val id: Int, //1526384
		val name: String, //Almaty
		val cod: Int, //200
		val message: String //city not found
)


data class Sys(
		val type: Int, //1
		val id: Int, //7191
		val message: Double, //0.0034
		val country: String, //KZ
		val sunrise: Int, //1515205450
		val sunset: Int //1515238343
)

data class Wind(
		val speed: Double, //0.41
		val deg: Double //85.501
)

data class Clouds(
		val all: Int //40
)

data class Weather(
		val id: Int, //701
		val main: String, //Mist
		val description: String, //mist
		val icon: String //50n
)

data class Coord(
		val lon: Double, //76.95
		val lat: Double //43.24
)

data class Main(
		val temp: Double, //259.15
		val pressure: Double, //1023
		val humidity: Int, //84
		val temp_min: Double, //259.15
		val temp_max: Double //259.15
)