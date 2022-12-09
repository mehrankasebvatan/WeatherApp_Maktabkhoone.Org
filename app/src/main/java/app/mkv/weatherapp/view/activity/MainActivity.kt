package app.mkv.weatherapp.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import app.mkv.weatherapp.R
import app.mkv.weatherapp.databinding.ActivityMainBinding
import app.mkv.weatherapp.model.Data
import app.mkv.weatherapp.viewmodel.DataViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataViewModel: DataViewModel
    private var cityName = "tehran"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgMenu.bringToFront()
        getData(cityName)
        event()


    }


    private fun event() {

        binding.apply {
            refresh.setOnRefreshListener {
                getData(cityName)
                refresh.isRefreshing = false
            }

            imgMenu.setOnClickListener {

                val menu = PopupMenu(this@MainActivity, it)
                menu.inflate(R.menu.menu_city)
                menu.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {
                        R.id.tehran -> {
                            cityName = "tehran"
                            getData(cityName)
                        }
                        R.id.paris -> {
                            cityName = "paris"
                            getData(cityName)
                        }
                        R.id.london -> {
                            cityName = "london"
                            getData(cityName)
                        }
                    }
                    true
                }
                menu.show()

            }

        }


    }


    private fun getData(cityName: String) {

        binding.loading.visibility = View.VISIBLE
        binding.loMain.visibility = View.GONE
        binding.imgMenu.visibility = View.GONE

        Handler(Looper.getMainLooper()).postDelayed({

            dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]
            dataViewModel.getData(this, cityName, "fa")
            dataViewModel.observeLiveData().observe(this) {
                fillPage(it)
                getIcon(it.weather[0].icon)

                when(cityName){
                    "tehran" -> binding.imgCity.setImageResource(R.drawable.tehran)
                    "paris" -> binding.imgCity.setImageResource(R.drawable.paris)
                    "london" -> binding.imgCity.setImageResource(R.drawable.london)
                }


                binding.loading.visibility = View.GONE
                binding.loMain.visibility = View.VISIBLE
                binding.imgMenu.visibility = View.VISIBLE

            }


        }, 1000)


    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun fillPage(data: Data) {
        getImage()
        binding.apply {
            txtName.text = data.name
            txtStatus.text = " وضعیت هوا: ${data.weather[0].description}"
            txtTemp.text = " دما: ${data.main.temp}"
            txtFellTemp.text = " دمای احساس شده: ${data.main.feels_like}"
            txtSunrise.text = getTime(data.sys.sunrise)
            txtSunset.text = getTime(data.sys.sunset)
            txtMinTemp.text = " حداقل دما: ${data.main.temp_min}"
            txtMaxTemp.text = " حداکثر دما: ${data.main.temp_max}"
            txtHumidity.text = " رطوبت هوا: ${data.main.humidity}"
            txtPressure.text = " فشار هوا: ${data.main.pressure}"
            txtWindSpeed.text = " سرعت باد: ${data.wind.speed}"
            txtWindDeg.text = " درجه باد: ${data.wind.deg}"

        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun getTime(t: Int): String {
        val time = t * 1000.toLong()
        val date = Date(time)
        val formatter = SimpleDateFormat("HH:mm a")
        return formatter.format(date)

    }


    private fun getIcon(id: String) {
        val url = "https://openweathermap.org/img/wn/${id}@2x.png"
        Picasso.get()
            .load(url)
            .into(binding.imgIcon)

        Log.d("IMAGE", url)
    }

    private fun getImage() {
        when (cityName) {

            "tehran" -> {}
            "london" -> {}
            "paris" -> {}


        }
    }


}