package com.beantherepodthat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.beantherepodthat.MainViewModel
import com.beantherepodthat.viewmodelstorage.IngredientsViewModel
import com.beantherepodthat.R
import com.beantherepodthat.databinding.DashboardFragmentBinding
import com.beantherepodthat.databinding.IngredientsFragmentBinding
import com.beantherepodthat.utils.loadImage
import kotlinx.android.synthetic.main.coffee_details_fragment.*

class IngredientsFragment : Fragment() {

    companion object {
        fun newInstance() = IngredientsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: IngredientsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = IngredientsFragmentBinding.inflate(inflater, container, false)
        viewModel= ViewModelProviders.of(this).get(MainViewModel::class.java)

        /*Get arugments via bundle*/
        val bundle = arguments?.getString("coffeeID")
        Log.d("BundleIngredients", bundle.toString())

        /*Refresh methods in viewmodel*/
        viewModel.refresh()

        /*Observe coffeelist and add ingredients to the screen*/
        viewModel.coffeeList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.coffeeList.value?.get(Integer.parseInt(bundle))?.let { Log.d("Coffeename: coffeelist:", it.coffeeName) }
            var cdCoffee = viewModel.coffeeList.value?.get(Integer.parseInt(bundle))

            if (cdCoffee != null) {
                binding.ingredients.text = "${cdCoffee.ingredients}"
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /*Navigate to Settings*/
        btnSettings.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_ingredientsFragment_to_settingsFragment)
        })

        /*Navigate to Dashboard*/
        btnDashBoard.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_ingredientsFragment_to_dashboardFragment)
        })

        /*Navigate to CoffeeList*/
        btnCoffeeList.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_ingredientsFragment_to_coffeeListFragment2)
        })
    }

}