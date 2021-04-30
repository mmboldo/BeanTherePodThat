package com.beantherepodthat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.beantherepodthat.model.Coffee
import com.beantherepodthat.model.MyCoffees
import com.beantherepodthat.model.User
import com.beantherepodthat.retrofit.ApiService
import kotlinx.coroutines.*

class MainViewModel(app: Application) : AndroidViewModel(app){

 var selectedCoffee = MutableLiveData<Coffee>()

    val TAG = "Get user data"

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val userData = MutableLiveData<User>()
    val coffeeList = MutableLiveData<List<Coffee>>()
    var userCoffees = listOf<MyCoffees>() // should this be a MutableLiveData as well?
    val myCoffees = MutableLiveData<List<MyCoffees>>()

    val usersLoadError = MutableLiveData<String?>()

    // This object stores the user's e-mail
    // It is later sent as an argument on API calls
    // Used by: API/getMyCoffees
    companion object {
        lateinit var userEmail : String
    }

    fun refresh(){
        fetchUserData()
        fetchCoffeeList()
    }

    // this tries to get the user info
    private fun fetchUserData() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
    //        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var user_email = userEmail
            val response = ApiService.
                    service
                    .getUserData(user_email)
                    Log.d("User Data Response:", response.toString())
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful){
                            userData.value = response.body()
                            // coffeeList.value = response.body()?.myCoffees
                            userData.value?.let { Log.d("Coffee Name:", it.myCoffees[1].coffeeName) }
                            userData.value?.let { Log.d("Coffee Size", it.myCoffees.size.toString()) }

                            for(i in 0 until userData.value?.myCoffees?.size!!){
                                var coffee = listOf(userData.value?.myCoffees!!.get(i))
                                userCoffees += coffee
                                Log.d("userCoffees", userCoffees.toString())
                                Log.d("userCoffees", userCoffees.toString())
                            }

                            myCoffees.value = userCoffees

                            Log.d("mycoffee:", myCoffees.value.toString())
                            usersLoadError.value = null
                        } else {
                            onError("Error : ${response.message()} ")
                        }
                    }
        }
        usersLoadError.value = ""
    }

    // this tries to get the general coffee list
    private fun fetchCoffeeList() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiService.service.getCoffeeList()
            Log.d("Coffee List Response:", response.toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    coffeeList.value = response.body()
                    coffeeList.value?.get(0)?.let { Log.d("Coffeename: coffeelist:", it.coffeeName) }
                    usersLoadError.value = null
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        usersLoadError.value = ""
    }
    private fun onError(message: String) {
       // usersLoadError.value = message
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}