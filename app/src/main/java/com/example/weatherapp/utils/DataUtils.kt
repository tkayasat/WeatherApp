import com.example.weatherapp.domain.Weather
import com.example.weatherapp.domain.getDefaultCity
import com.example.weatherapp.repository.WeatherDTO

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    return Weather(
        getDefaultCity(),
        weatherDTO.fact.temp,
        weatherDTO.fact.feels_like,
        weatherDTO.fact.icon
    )
}