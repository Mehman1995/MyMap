package ru.maxpek.placesoftravel.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.maxpek.placesoftravel.R
import ru.maxpek.placesoftravel.databinding.AppActivityBinding

@AndroidEntryPoint
class AppActivity : AppCompatActivity(){

    companion object{
        private const val MAPKIT_API_KEY = "e0f40ead-fefb-45cf-821c-37efc0eaa548"
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        val binding = AppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}