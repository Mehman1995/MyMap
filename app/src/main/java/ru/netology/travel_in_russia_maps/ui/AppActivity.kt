package ru.netology.travel_in_russia_maps.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.travel_in_russia_maps.databinding.AppActivityBinding


class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = AppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

