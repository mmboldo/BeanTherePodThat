package com.beantherepodthat.ui

import android.content.ContentValues.TAG
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.RatingBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.beantherepodthat.MainViewModel
import com.beantherepodthat.R
import com.beantherepodthat.databinding.CoffeeDetailsFragmentBinding
import com.beantherepodthat.retrofit.ApiService

import com.beantherepodthat.utils.loadImage

import kotlinx.android.synthetic.main.coffee_details_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class CoffeeDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CoffeeDetailsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: CoffeeDetailsFragmentBinding
    var favorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CoffeeDetailsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        /*Get arguments from bundle*/
        val bundle = arguments?.getString("coffeeID")
        Log.d("Bundle", bundle.toString())

        /*Observes coffeelist and connects the adapter to the ui*/
        viewModel.refresh()
        viewModel.coffeeList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.coffeeList.value?.get(Integer.parseInt(bundle))?.let { Log.d("Coffeename: coffeelist:", it.coffeeName) }
            var cdCoffee = viewModel.coffeeList.value?.get(Integer.parseInt(bundle))

            /*Binds UI to the coffee information*/
            if (cdCoffee != null) {
                binding.imgSelectedCoffee.loadImage(cdCoffee.coffeeImg)
                binding.cdCoffeeName.text = "${cdCoffee.coffeeName}"
                binding.cdCoffeeBrand.text = "${cdCoffee.brand}"
                binding.cdCoffeeDescription.text = "${cdCoffee.description}"
                binding.intensityValue.text = "${cdCoffee.intensity}"
                binding.cdSize.text = "${cdCoffee.cupSize}"
                binding.bodyValue.text = "${cdCoffee.body}"
                binding.roastValue.text = "${cdCoffee.roast}"
                binding.acidityValue.text = "${cdCoffee.acidity}"
                binding.biternessValue.text = "${cdCoffee.bitterness}"

                //Gets rating from Rating Bar
                btnRate.setOnClickListener(View.OnClickListener {
                    ratingBar.rating
                    Log.d("Rating", ratingBar.rating.toString())

                    var user_email = MainViewModel.userEmail

                    Log.d("Favorite", favorite.toString())
                    // Adding the rated coffee to MyCoffee List inside User
                    ApiService
                            .service
                            .registerMyCoffee(user_email, cdCoffee.id, cdCoffee.coffeeName, cdCoffee.coffeeImg, cdCoffee.brand, cdCoffee.description, cdCoffee.intensity,
                            cdCoffee.cupSize, cdCoffee.roast, cdCoffee.acidity, cdCoffee.bitterness, cdCoffee.body, cdCoffee.ingredients,
                            cdCoffee.machine, ratingBar.rating, favorite)
                            .enqueue(object :retrofit2.Callback<ResponseBody>{
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Log.d(TAG,t.message.toString())
                                }

                                override fun onResponse(
                                        call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                                ) {
                                    if(response.isSuccessful){
                                        val message = response.body()?.string().toString()
                                        Log.d(TAG,message)
                                        Toast.makeText(activity, "Add this coffee to my list", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                })
            }
        })



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /*Navigate to Settings*/
        btnSettings.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_coffeeDetailsFragment_to_settingsFragment)
        })

        /*Navigate to Dashboard*/
        btnDashBoard.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_coffeeDetailsFragment_to_dashboardFragment)
        })

        /*Navigate to CoffeeList*/
        btnCoffeeList.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_coffeeDetailsFragment_to_coffeeListFragment2)
        })

        /*Navigate to Ingredients*/
        bntIngredients.setOnClickListener(View.OnClickListener {

            val bundleID = Bundle()
            val bundle = arguments?.getString("coffeeID")
            Log.d("Bundle2", bundle.toString())
            bundleID.putString("coffeeID", bundle.toString())

            findNavController().navigate(R.id.action_coffeeDetailsFragment_to_ingredientsFragment, bundleID)
        })

        /*Button Favorite*/
        btnFavorite.setOnClickListener(View.OnClickListener {
            if(favorite == true){
                btnFavorite.setImageResource(R.drawable.notfavorite)
                favorite = false
            }
            else{
                btnFavorite.setImageResource(R.drawable.favorite)
                favorite = true
            }
            Log.d("Favorite", favorite.toString())
        })
    }
}