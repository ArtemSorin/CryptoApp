package com.example.cryptoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
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
                println("Failed Connection")
            }
        }
    }

    private fun updateUI(request: Request)
    {
        runOnUiThread {
            kotlin.run {
                val list = findViewById<ListView>(R.id.recipe_list_view)
                val array = arrayOf(
                    request.data[0].symbol + ": " + String.format("%.3f", request.data[0].volume24),
                    request.data[1].symbol + ": " + String.format("%.3f", request.data[1].volume24),
                    request.data[2].symbol + ": " + String.format("%.3f", request.data[2].volume24),
                    request.data[3].symbol + ": " + String.format("%.3f", request.data[3].volume24),
                    request.data[4].symbol + ": " + String.format("%.3f", request.data[4].volume24),
                    request.data[5].symbol + ": " + String.format("%.3f", request.data[5].volume24),
                    request.data[6].symbol + ": " + String.format("%.3f", request.data[6].volume24),
                )
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
                list.adapter = adapter
                list.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this, CurrentCryptoInfoActivity()::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}