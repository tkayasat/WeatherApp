import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getDefaultCity
import com.example.weatherapp.repository.WeatherDTO
import com.example.weatherapp.room.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    return Weather(
        getDefaultCity(),
        weatherDTO.fact.temp,
        weatherDTO.fact.feels_like,
        weatherDTO.fact.icon
    )
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(
            City(it.name, 0.0, 0.0),
            it.temperature,
            0,
        )
    }
}

fun convertWeatherToHistoryEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(
        0,
        weather.city.name,
        weather.temperature,
    )
}