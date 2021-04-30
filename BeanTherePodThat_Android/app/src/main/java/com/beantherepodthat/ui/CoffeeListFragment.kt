package com.beantherepodthat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.beantherepodthat.MainViewModel
import com.beantherepodthat.R
import com.beantherepodthat.adapter.CoffeeListAdapter
import com.beantherepodthat.adapter.MyCoffeeAdapter
import com.beantherepodthat.databinding.CoffeeListFragmentBinding
import kotlinx.android.synthetic.main.coffee_list_fragment.*

class CoffeeListFragment : Fragment(), CoffeeListAdapter.ListItemListener {

    companion object {
        fun newInstance() = CoffeeListFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : CoffeeListFragmentBinding
    private lateinit var adapter : CoffeeListAdapter
    private lateinit var adapterMyCoffee : MyCoffeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CoffeeListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.refresh()

        //Adapter Decoration
        with(binding.recyclerViewCoffeeList) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }

        /*Observe list and create adapter*/
        viewModel.coffeeList?.observe(viewLifecycleOwner, Observer {

            binding.recyclerViewCoffeeList.layoutManager = LinearLayoutManager(activity)
            adapter = CoffeeListAdapter(it, this)
            binding.recyclerViewCoffeeList.adapter = adapter
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        /*Change adapter to MyPods*/
        btnMyPods.setOnClickListener(View.OnClickListener {
            Log.i("BtnMyPods ", "Is clicked")

            /*Set colours*/
            btnMyPods.setBackgroundColor(getResources().getColor(R.color.lightGreen))
            btnExplorePods.setBackgroundColor(getResources().getColor(R.color.greenBackground))

            viewModel.userData?.observe(viewLifecycleOwner, Observer {
                binding.recyclerViewCoffeeList.layoutManager = LinearLayoutManager(activity)
                adapterMyCoffee = MyCoffeeAdapter(it)
                binding.recyclerViewCoffeeList.adapter = adapterMyCoffee
            })
        })

        /*Change Adapter to Explore Pods*/
        btnExplorePods.setOnClickListener(View.OnClickListener {
            /*Set colours*/
            btnMyPods.setBackgroundColor(getResources().getColor(R.color.greenBackground))
            btnExplorePods.setBackgroundColor(getResources().getColor(R.color.lightGreen))


            viewModel.coffeeList?.observe(viewLifecycleOwner, Observer {
                binding.recyclerViewCoffeeList.layoutManager = LinearLayoutManager(activity)
                adapter = CoffeeListAdapter(it, this)
                binding.recyclerViewCoffeeList.adapter = adapter
            })
        })

        /*Navigat to Settings*/
        btnSettings.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_coffeeListFragment2_to_settingsFragment)
        })

        /*Navigate to Dashboard*/
        btnDashBoard.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_coffeeListFragment2_to_dashboardFragment)
        })
    }

    /*View coffee controls on click on the adapter*/
    override fun viewCoffee(coffeeId: Int) {
        Log.i("viewCoffee", "coffeeId = " + coffeeId + " clicked")
        val bundle = Bundle()
        bundle.putString("coffeeID", coffeeId.toString())

        findNavController().navigate(
            R.id.action_coffeeListFragment2_to_coffeeDetailsFragment,
            bundle
        )
    }

    override fun onItemSelectionChanged() {
        TODO("Not yet implemented")
    }
}