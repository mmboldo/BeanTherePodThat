package com.beantherepodthat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.beantherepodthat.R
import com.beantherepodthat.viewmodelstorage.SettingsViewModel
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        /*Navigate to Dashboard*/
        btnDashBoard.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_dashboardFragment)
        })

        /*Navigate to CoffeeList*/
        btnCoffeeList.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_coffeeListFragment2)
        })

        /*Navigate to Login*/
        btnSignOut.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        })
    }

}