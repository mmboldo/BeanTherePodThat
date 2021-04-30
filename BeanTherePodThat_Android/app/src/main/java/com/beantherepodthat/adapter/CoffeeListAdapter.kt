package com.beantherepodthat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beantherepodthat.R
import com.beantherepodthat.databinding.ItemListBinding
import com.beantherepodthat.model.Coffee
import com.beantherepodthat.utils.loadImage

/*Coffee List Adapter that uses a List Item Listener*/
class CoffeeListAdapter(private val coffeelist: List<Coffee>, private val listener: ListItemListener):
RecyclerView.Adapter<CoffeeListAdapter.ViewHolder>() {

    val selectedCoffee = arrayListOf<Coffee>()

    interface ListItemListener {
        fun viewCoffee(coffeeId: Int)
        fun onItemSelectionChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coffeelist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coffee = coffeelist[position]
        with(holder.binding){
            imgCoffee.loadImage(coffee.coffeeImg)
            coffeeName.text = coffee.coffeeName
            coffeeBrand.text = coffee.brand
            root.setOnClickListener {
                Log.d("CoffeeID ONcLICK", coffee.id.toString())
                listener.viewCoffee(position)
            }
        }
    }

}