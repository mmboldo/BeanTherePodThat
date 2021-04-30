package com.beantherepodthat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.beantherepodthat.R
import com.beantherepodthat.viewmodelstorage.SignUpViewModel
import com.beantherepodthat.retrofit.ApiService
import kotlinx.android.synthetic.main.sign_up_fragment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class SignUpFragment : Fragment() {

    val TAG = "Register"

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel


        buttonSignUp.setOnClickListener {

            val email = editTextEmail.text.toString();
            Log.d(TAG,email)
            val firstname = editTextFirstName.text.toString();
            val lastname = editTextLastName.text.toString();
            val password = editTextPassword.text.toString();

            ApiService
                .service
                .registerUser(email, firstname, lastname, password)
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
                            Toast.makeText(activity, "Registration complete", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        textViewSignIn.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }
}