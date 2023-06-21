package com.example.uts_160420029_jeremy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uts_160420029_jeremy.R
import com.example.uts_160420029_jeremy.viewmodel.CategoryVM

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: CategoryVM
private val catListAdapter = FoodCategoryAdapter(arrayListOf())

/**
 * A simple [Fragment] subclass.
 * Use the [FoodCategory.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodCategory : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CategoryVM::class.java)
        viewModel.refresh()

        val recView = view.findViewById<RecyclerView>(R.id.recViewFC)
        val progressLoad = view.findViewById<ProgressBar>(R.id.progressLoadFC)
        val txtError = view.findViewById<TextView>(R.id.txtErrorFC)
        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutFC)
        val fabRec: View = view.findViewById<SwipeRefreshLayout>(R.id.fabRec)
        val fabPromo: View = view.findViewById<SwipeRefreshLayout>(R.id.fabPromo)

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = catListAdapter
        observeViewModel(txtError, recView, progressLoad)

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            txtError.visibility = View.GONE
            progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        fabRec.setOnClickListener {
            val action = FoodCategoryDirections.actionItemCategoryToRecommendation()
            Navigation.findNavController(it).navigate(action)
        }

        fabPromo.setOnClickListener {
            val action = FoodCategoryDirections.actionPromoList()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun observeViewModel(txtError: TextView, recView: RecyclerView, progressLoad: ProgressBar) {
        viewModel.categoryListLD.observe(viewLifecycleOwner, Observer {
            catListAdapter.updateCatList(it)
        })

        viewModel.categoryLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                txtError.visibility = View.VISIBLE
            } else {
                txtError.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                recView.visibility = View.GONE
                progressLoad.visibility = View.VISIBLE
            } else {
                recView.visibility = View.VISIBLE
                progressLoad.visibility = View.GONE
            }
        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FoodCategory.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodCategory().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}