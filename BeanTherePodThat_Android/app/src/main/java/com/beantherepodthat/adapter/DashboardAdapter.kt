package com.beantherepodthat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beantherepodthat.R
import com.beantherepodthat.databinding.CoffeePodBinding
import com.beantherepodthat.utils.loadImage
import com.beantherepodthat.model.User

/*Dashboard Adapter that access MyCoffee List inside User*/
class DashboardAdapter(private val coffeedashboard: User, private val listener: DashboardAdapter.ListItemListener):
        RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    interface ListItemListener {
        fun viewCoffee(coffeeId: Int)
        fun onItemSelectionChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = CoffeePodBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.coffee_pod, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardAdapter.ViewHolder, position: Int) {
        val coffee = coffeedashboard.myCoffees.get(position)
        with(holder.binding){
            coffeeImg.loadImage(coffee.coffeeImg)
            coffeeTitle.text = coffee.coffeeName
        }
    }

    override fun getItemCount(): Int {
        if(coffeedashboard.myCoffees.size > 5){
            return 5
        }
        else return coffeedashboard.myCoffees.size
    }
}