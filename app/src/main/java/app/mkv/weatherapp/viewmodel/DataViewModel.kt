package app.mkv.weatherapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.mkv.weatherapp.api.ApiInstance
import app.mkv.weatherapp.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel : ViewModel() {

    private var liveData = MutableLiveData<Data>()

    fun getData(context: Context, cityName: String, lang: String) {


        ApiInstance.api.getData(cityName, "1e462cfda7cd3d6d55a821723248252e", lang, "metric")
            .enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if (response.body() != null) {
                        liveData.value = response.body()
                    } else return


                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_LONG).show()


                }

            })

    }

    fun observeLiveData(): LiveData<Data> {
        return liveData
    }

}