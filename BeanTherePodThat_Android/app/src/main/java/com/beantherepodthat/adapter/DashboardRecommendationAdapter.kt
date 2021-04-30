package com.beantherepodthat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beantherepodthat.R
import com.beantherepodthat.databinding.CoffeePodBinding
import com.beantherepodthat.model.Coffee
import com.beantherepodthat.model.MyCoffees
import com.beantherepodthat.model.User
import com.beantherepodthat.utils.loadImage

/*Dashboard Adapter that access all coffees */
class DashboardRecommendationAdapter(private val coffeerecomendation: List<Coffee>):
        RecyclerView.Adapter<DashboardRecommendationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = CoffeePodBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardRecommendationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.coffee_pod, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardRecommendationAdapter.ViewHolder, position: Int) {
        val coffee = coffeerecomendation[position]
        with(holder.binding){
            coffeeImg.loadImage(coffee.coffeeImg)
            coffeeTitle.text = coffee.coffeeName
        }
    }

    override fun getItemCount(): Int {
        if(coffeerecomendation.size > 5){
            return 5
        }
        else return coffeerecomendation.size
    }
}