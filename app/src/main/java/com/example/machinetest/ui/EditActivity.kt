package com.example.machinetest.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.machinetest.R
import com.example.machinetest.model.Population

class EditActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




      val population =  intent.getSerializableExtra("population",Population::class.java)

        val tvNation = findViewById<TextView>(R.id.tvNationvalue)
        val tvYear = findViewById<TextView>(R.id.tvYearValue)
        val edtPopulation = findViewById<EditText>(R.id.userEdittext)
        tvNation.text = population?.Nation
        tvYear.text = population?.Year
        edtPopulation.setText(population?.Population.toString())


        findViewById<Button>(R.id.updateBtn).setOnClickListener {
            if(edtPopulation.text.isNotEmpty()){
                val populationEditText = edtPopulation.text.toString()
                population?.Population = populationEditText.toLong()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("population_updated", population)
                setResult(RESULT_OK,intent)
                finish()
            }




        }






    }
}