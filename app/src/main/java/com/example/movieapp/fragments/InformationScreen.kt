package com.example.movieapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.FragmentInformationScreenBinding
import com.example.movieapp.room.Movies

class InformationScreen : Fragment() {
    private var viewBinding: FragmentInformationScreenBinding? = null
    private val binding get() = viewBinding!!
    private var positionNumberID = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentInformationScreenBinding.inflate(inflater, container, false)
        val getObject = arguments?.getParcelable<Movies>("position_id")
        if (getObject != null) {
            positionNumberID = getObject.id!!
            binding.txtAppbar.setText(getObject.name)
            binding.etName.setText("Movie Name: "+ getObject.name)
            binding.etAuthor.setText("Movie Author: "+ getObject.author)
            binding.etDefinition.setText("About movie: " + getObject.definition)
            binding.etDate.setText("Date: "+getObject.date)
            // Log data
            Log.d("InformationScreen", "Name: ${getObject.name}")
            Log.d("InformationScreen", "Author: ${getObject.author}")
            Log.d("InformationScreen", "Definition: ${getObject.definition}")
            Log.d("InformationScreen", "Date: ${getObject.date}")
        } else {
            Log.d("InformationScreen", "getObject is null")
        }
        binding.etClose.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewBinding?.root
    }
}
