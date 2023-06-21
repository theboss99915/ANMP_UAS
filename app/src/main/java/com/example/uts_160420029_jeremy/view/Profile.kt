package com.example.uts_160420029_jeremy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uts_160420029_jeremy.R
import com.example.uts_160420029_jeremy.util.loadImage
import com.example.uts_160420029_jeremy.viewmodel.UserVM

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: UserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(UserVM::class.java)
        viewModel.fetch()

        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)
        val txtBod = view.findViewById<TextView>(R.id.txtBod)
        val txtPhone = view.findViewById<TextView>(R.id.txtPhone)
        var imageView = view.findViewById<ImageView>(R.id.imageView2)
        var progressBar = view.findViewById<ProgressBar>(R.id.progressBar2)

        observeViewModel(txtName, txtEmail, txtBod, txtPhone, imageView, progressBar)
    }

    fun observeViewModel(txtName: TextView, txtEmail: TextView, txtBod: TextView, txtPhone: TextView, imageView: ImageView, progressBar: ProgressBar) {
        viewModel.userDD.observe(viewLifecycleOwner, Observer {
            var user = it
            txtName.text = user.name
            txtEmail.text = user.email
            txtBod.text = user.bod
            txtPhone.text = user.phone

            imageView.loadImage(user.photoUrl, progressBar)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                Profile().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}