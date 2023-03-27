package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import com.example.todoapp.databinding.FragmentAddActivityBinding
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddActivityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddActivityFragment : Fragment() {

    private  val  viewModel: ActivityFragmentViewModel  by activityViewModels()
    private val viewModelUser : UserViewModel by activityViewModels()
    private  lateinit var  retService: ActivityService
    private lateinit var binding:FragmentAddActivityBinding;
    private var param1: String? = null
    private var param2: String? = null
    var title:String?=null
    var content:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        retService = RetrofitInstance.getRetrofitInstance().create(ActivityService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAddActivityBinding.inflate(inflater, container, false)
        binding.addActivity.setOnClickListener{
            title = binding.editTextTextPersonName2.text.toString()
            content = binding.multiAutoCompleteTextView.text.toString()
            if(title.isNullOrEmpty() || content.isNullOrEmpty()){
                Toast.makeText(activity,"please add a activity",Toast.LENGTH_SHORT).show()
            }else {



                viewModelUser.user.observe(viewLifecycleOwner, { user ->
                    val responseLiveData: LiveData<Response<Activity>> = liveData {
                        val response = retService.postActivity(title!!, content!!, user.id)

                        Log.i("POST",response.toString())
                    }

                })



//                it.findNavController().navigate(R.id.action_addActivityFragment_to_activityFragment)

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
         * @return A new instance of fragment AddActivityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddActivityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}