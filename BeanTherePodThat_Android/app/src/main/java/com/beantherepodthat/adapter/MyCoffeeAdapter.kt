package com.beantherepodthat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beantherepodthat.R
import com.beantherepodthat.databinding.MycoffeeItemBinding
import com.beantherepodthat.model.MyCoffees
import com.beantherepodthat.model.User
import com.beantherepodthat.utils.loadImage

/*Adapter that access MyCoffee List inside User*/
class MyCoffeeAdapter(private val mycoffeelist: User):
    RecyclerView.Adapter<MyCoffeeAdapter.ViewHolder>()  {

    val selectedMyCoffee = arrayListOf<MyCoffees>()

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = MycoffeeItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCoffeeAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.mycoffee_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mycoffeelist.myCoffees.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myCoffee = mycoffeelist.myCoffees.get(position)
        with(holder.binding){
            mycoffeeimg.loadImage(myCoffee.coffeeImg)
            coffeeBrandinMyCoffee.text = myCoffee.brand
            coffeeNameinMyCoffee.text = myCoffee.coffeeName
            coffeeRating.text = myCoffee.rating.toString()

            if(myCoffee.favorite .equals("true")){
                favorite.setImageResource(R.drawable.favorite)
            }
            if(myCoffee.favorite){
                favorite.setImageResource(R.drawable.favorite)
            }
            else{favorite.setImageResource(R.drawable.notfavorite)}
        }
    }


}