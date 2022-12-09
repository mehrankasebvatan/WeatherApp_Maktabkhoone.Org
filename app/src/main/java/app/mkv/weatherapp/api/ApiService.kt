package app.mkv.weatherapp.api

import app.mkv.weatherapp.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun getData(
        @Query("q") cityName: String,
        @Query("appid") key: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ) : Call<Data>


}