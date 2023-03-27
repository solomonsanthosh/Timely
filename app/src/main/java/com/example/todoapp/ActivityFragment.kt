package com.example.todoapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.FragmentActivityBinding
import retrofit2.Response
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ActivityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActivityFragment : Fragment() {

    private  val  viewModel: ActivityFragmentViewModel  by activityViewModels()
    private  lateinit var  retService: ActivityService


    private  val  viewModelUser: UserViewModel  by activityViewModels()
    private  var  userData: User = User(0,"");
    private  var  activityList: List<Activity> = mutableListOf(Activity(0,"","",0))
    private lateinit var  binding: FragmentActivityBinding;
    private var param1: String? = null
    private var param2: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        retService = RetrofitInstance.getRetrofitInstance().create(ActivityService::class.java)









    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment




       binding =   FragmentActivityBinding.inflate(inflater, container, false)
        var recyclerView = binding.recyclerView;
        recyclerView.setBackgroundColor(Color.BLACK);
        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModelUser.user.observe(viewLifecycleOwner,{user->
            userData = user
        })
        val responseLiveData: LiveData<Response<List<Activity>>> = liveData {
            val response = retService.getActivities(userData.id)
            emit(response)
        }
        responseLiveData.observe(viewLifecycleOwner, Observer {
            val activity = it.body()!!

            if(activity!=null){
                viewModel.updateActivity(activity)
                viewModel.activityList.observe(viewLifecycleOwner,{activity->
                    activityList = activity
                })
            }
         recyclerView.adapter = MyRecyclerViewAdaptor(activityList)

        })
        binding.floatingActionButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_activityFragment_to_addActivityFragment)
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
         * @return A new instance of fragment ActivityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ActivityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}