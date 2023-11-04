package com.example.movieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MovieViewModel
import com.example.movieapp.MoviesAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMainScreenBinding

class MainScreen : Fragment(R.layout.fragment_main_screen), MoviesAdapter.OnUserClickedListener {
    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var rvAdapter: MoviesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainScreenBinding.bind(view)
        viewModel.getAllMovies().observe(viewLifecycleOwner, Observer { list ->
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            rvAdapter = MoviesAdapter(requireContext(), list)
            binding.recyclerView.adapter = rvAdapter
            rvAdapter.setOnUserClickedListener(this)
        })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_addScreen)
        }
    }


    override fun onUserClicked(position: Int) {
        Toast.makeText(context, "bosildi - $position -", Toast.LENGTH_SHORT).show()
    }
}
