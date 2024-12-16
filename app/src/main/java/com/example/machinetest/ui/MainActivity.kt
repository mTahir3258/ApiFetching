package com.example.machinetest.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetest.PopulationAdapter
import com.example.machinetest.R
import com.example.machinetest.api.RequestApi
import com.example.machinetest.manager.ApiManager
import com.example.machinetest.model.Population
import com.example.machinetest.model.PopulationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : PopulationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        // call the api
        val requestPopulation = ApiManager.getClient(RequestApi::class.java)
       val call = requestPopulation.fetchData("Nation","Population")

        call.enqueue(object : Callback<PopulationResponse> {
            override fun onResponse(
                p0: Call<PopulationResponse>,
                response: Response<PopulationResponse>
            ) {
                progressBar.visibility = View.GONE
                response.body()?.data?.let { list ->
                    // set up recycler view
                    setUpRecyclerView(list)
                }
            }

            override fun onFailure(p0: Call<PopulationResponse>, p1: Throwable) {
                // Error
                Toast.makeText(applicationContext, "Request failed", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })

    }

    private fun setUpRecyclerView(list: List<Population>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        if (list.isNotEmpty()) {
            adapter = PopulationAdapter(this, list)
            recyclerView.adapter = adapter
        }

    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val population = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getSerializableExtra("population_updated",Population::class.java)
            } else {
                intent?.getSerializableExtra("population_updated") as Population
            }
            population?.let {
                adapter.updateValue(population)
            }

        }
    }
}