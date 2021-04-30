package com.beantherepodthat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.beantherepodthat.MainViewModel
import com.beantherepodthat.viewmodelstorage.DashboardViewModel
import com.beantherepodthat.R
import com.beantherepodthat.adapter.CoffeeListAdapter
import com.beantherepodthat.adapter.DashboardAdapter
import com.beantherepodthat.adapter.DashboardRecommendationAdapter
import com.beantherepodthat.databinding.DashboardFragmentBinding
import kotlinx.android.synthetic.main.dashboard_fragment.*

class DashboardFragment : Fragment(), DashboardAdapter.ListItemListener{

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: DashboardAdapter
    private lateinit var recAdapter: DashboardRecommendationAdapter
    private lateinit var binding: DashboardFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DashboardFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.refresh()

        /*Observe UserData list to get the list*/
        viewModel.userData?.observe(viewLifecycleOwner, Observer {
            Log.d("Dashoard", "Got here")
            binding.recyclerViewYourTop5.layoutManager = GridLayoutManager(activity, 5)
            adapter = DashboardAdapter(it, this)
            binding.recyclerViewYourTop5.adapter = adapter

            /*Calculate averages*/
            var acidity: Int = 0
            var intensity: Int = 0
            var body: Int = 0
            var bitterness: Int = 0
            var roast: Int = 0

            /*Go through the list*/
            for (coffee in it.myCoffees){
                acidity += Integer.parseInt(coffee.acidity.toString())
                intensity += Integer.parseInt(coffee.intensity.toString())
                body += Integer.parseInt(coffee.body.toString())
                bitterness += Integer.parseInt(coffee.bitterness.toString())
                roast += Integer.parseInt(coffee.roast.toString())
            }

            /*divide by size*/
            var totalacidity = acidity / it.myCoffees.size
            var totalIntensity = intensity / it.myCoffees.size
            var totalBody = body / it.myCoffees.size
            var totalBitterness = bitterness / it.myCoffees.size
            var totalRoasr =roast / it.myCoffees.size

            /*Set Taste Profile*/
            binding.valueAcidity.text = totalacidity.toString()
            binding.valueIntensity.text = totalIntensity.toString()
            binding.valueBody.text = totalBody.toString()
            binding.cupSize.text = viewModel.userData.value?.myCoffees?.get(0)?.cupSize
            binding.valueBiterness.text = totalBitterness.toString()
            binding.valueRoast.text = totalRoasr.toString()

            Log.d("Acidity", viewModel.userData.value?.myCoffees?.get(0)?.acidity.toString())
        })

        /* Observes coffeelist and sets adapter*/
        viewModel.coffeeList?.observe(viewLifecycleOwner, Observer {
            Log.d("DashCoffeeList", viewModel.coffeeList.value?.size.toString())
            binding.recyclerViewSuggestions.layoutManager = GridLayoutManager(activity, 5)
            recAdapter = DashboardRecommendationAdapter(it)
            binding.recyclerViewSuggestions.adapter = recAdapter
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.refresh()

        /*Navigate to CoffeeList*/
        btnCoffeeList.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_coffeeListFragment2)
        })

        /*Navigate to Settings*/
        btnSettings.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_settingsFragment)
        })
    }

    override fun viewCoffee(coffeeId: Int) {
        Log.i("viewCoffee", "coffeeId = " + coffeeId + " clicked")
        val bundle = Bundle()
        bundle.putString("coffeeID", coffeeId.toString())

        findNavController().navigate(
                R.id.action_dashboardFragment_to_coffeeDetailsFragment,
                bundle
        )
    }
    override fun onItemSelectionChanged() {
        TODO("Not yet implemented")
    }
}