package com.example.cryptoapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCurrencyData().start()
    }
    @SuppressLint("SetTextI18n")
    private fun fetchCurrencyData(): Thread
    {
        return Thread {
            val url = URL("https://api.coinlore.net/api/tickers/")
            val connection  = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.baseCurrency.text = "Failed Connection"
            }
        }
    }

    private fun updateUI(request: Request)
    {
        runOnUiThread {
            kotlin.run {
                //binding.lastUpdated.text = request.time_last_update_utc
                binding.nzd.text = String.format("${request.data[0].symbol} %.2f", request.data[0].volume24)
                binding.usd.text = String.format("${request.data[1].symbol}: %.2f", request.data[1].volume24)
                binding.gbp.text = String.format("${request.data[2].symbol}: %.2f", request.data[2].volume24)
            }
        }
    }
}