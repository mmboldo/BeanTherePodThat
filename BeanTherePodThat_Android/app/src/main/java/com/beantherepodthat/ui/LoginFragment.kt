package com.beantherepodthat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.beantherepodthat.R
import kotlinx.android.synthetic.main.login_fragment.*
import android.util.Log
import android.widget.Toast
import com.beantherepodthat.MainViewModel.Companion.userEmail
import com.beantherepodthat.retrofit.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {

    val TAG = "Login"

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // This method is responsible for executing the user login through the backend
        buttonSignIn.setOnClickListener {

            val email = emailLogin.text.toString();
            Log.d(TAG,email)
            val password = passwordLogin.text.toString();

            ApiService
                .service
                .loginUser(email,password)
                .enqueue(object :retrofit2.Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d(TAG,"Login failed")

                        Log.d(TAG,t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        //Check is response is successful or not
                        if(response.isSuccessful){
                            val message = response.body()?.string().toString()
                            Log.d(TAG,"Login successful")
                            Log.d(TAG,message)
                            //If else controls message text that allows user to login
                            if(message.toString() == "Unknown e-mail. Are you registered?"){
                                Toast.makeText(context, "User or Password invalid", Toast.LENGTH_SHORT).show()
                            }else{
                                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                                userEmail = email // this is the user currently logged to the app.
                                Log.d("User email:", userEmail)
                            }
                        }
                    }
                })
        }

        textViewSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}