package com.example.todoapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private  val  viewModel: UserViewModel  by activityViewModels()
    private  lateinit var  retService: UserService
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : FragmentLoginBinding
    private var param1: String? = null
    private var param2: String? = null
    private var email:String? = null;
    private var password:String?  = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
        retService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            email = binding.editTextTextEmailAddress.text.toString();
            password = binding.editTextTextPassword2.text.toString();

            if(email.isNullOrEmpty() && password.isNullOrEmpty()){
                Toast.makeText(activity,"Please fill the input",Toast.LENGTH_SHORT).show()

            } else {
                auth.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {

                            val user = auth.currentUser
                            val responseLiveData: LiveData<Response<User>> = liveData {
                                val response = retService.getUser(email!!.lowercase())
                                emit(response)
                            }
                            responseLiveData.observe(viewLifecycleOwner, Observer {
                                val user = it.body()!!
                                viewModel.updateUser(user.id,user.email)
                                Log.i("tagme", user.email)
                             Toast.makeText(activity, "Login success.",
                                   Toast.LENGTH_SHORT).show()

                                findNavController().navigate(R.id.action_loginFragment_to_activityFragment)
                            })



                        } else {

                            Toast.makeText(activity, "Login failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }

            }
        }


        return binding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}