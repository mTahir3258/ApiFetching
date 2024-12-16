package com.example.machinetest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetest.model.Population
import com.example.machinetest.ui.EditActivity
import com.example.machinetest.ui.MainActivity

class PopulationAdapter(private var context: Context, var datalist: List<Population>) :
    RecyclerView.Adapter<PopulationAdapter.PopulationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopulationViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return PopulationViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopulationViewHolder, position: Int) {
        val population = datalist[position]
        holder.bindData(population)

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun updateValue(population: Population){
      val element =  datalist.find { it.Year == population.Year }
        element?.let {
           val position = datalist.indexOf(element)
            element.Population = population.Population
            notifyItemChanged(position, element)

        }
    }


    class PopulationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val tvNation = itemView.findViewById<TextView>(R.id.nation_value)
        private val tvYear = itemView.findViewById<TextView>(R.id.year_value)
        private val tvPopulation = itemView.findViewById<TextView>(R.id.population_value)
        private lateinit var population: Population

        init {
            itemView.setOnClickListener {
                if(itemView.context is MainActivity){
                    val mainActivity = itemView.context as MainActivity
                    val intent = Intent(itemView.context, EditActivity::class.java)
                    intent.putExtra("population",population)
                    mainActivity.startForResult.launch(intent)
                }

            }
        }

        fun bindData(population: Population) {
            this.population = population
            tvYear.text = population.Year
            tvNation.text = population.Nation
            tvPopulation.text = population.Population.toString()
        }


    }

}