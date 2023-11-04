package com.example.movieapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.MovieViewModel
import com.example.movieapp.databinding.FragmentEditScreenBinding
import com.example.movieapp.room.Movies

class EditScreen : Fragment() {
    private var viewBinding: FragmentEditScreenBinding? = null
    private val binding get() = viewBinding!!
    private val viewModel: MovieViewModel by viewModels()

    private var positionNumberID = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEditScreenBinding.inflate(inflater, container, false)
        val getObject = arguments?.getParcelable<Movies>("position_id")
        println("getObject = $getObject")
        positionNumberID = getObject?.id!!
        binding.etName.setText(getObject.name)
        binding.etAuthor.setText(getObject.author)
        binding.etDefinition.setText(getObject.definition)
        // Log data
        Log.d("InformationScreen", "Name: ${getObject?.name}")
        Log.d("InformationScreen", "Author: ${getObject?.author}")
        Log.d("InformationScreen", "Definition: ${getObject?.definition}")
        Log.d("InformationScreen", "Date: ${getObject?.date}")

        binding.etDate.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var ignoreNextTextChange = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (ignoreNextTextChange) {
                    ignoreNextTextChange = false
                    return
                }

                val text = s?.toString() ?: ""
                val formattedText = formatDate(text)
                if (formattedText != text) {
                    isFormatting = true
                    binding.etDate.setText(formattedText)
                    binding.etDate.setSelection(formattedText.length)
                }
            }
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) {
                    isFormatting = false
                    return
                }
                val text = s?.toString() ?: ""
                val cleanedText = cleanDate(text)
                if (cleanedText != text) {
                    ignoreNextTextChange = true
                    binding.etDate.setText(cleanedText)
                    binding.etDate.setSelection(cleanedText.length)
                }
            }
            private fun formatDate(text: String): String {
                val digitsOnly = text.replace("[^0-9]".toRegex(), "")
                val sb = StringBuilder(digitsOnly)
                if (digitsOnly.length >= 5) {
                    sb.insert(2, "/")
                    sb.insert(5, "/")
                } else if (digitsOnly.length >= 3) {
                    sb.insert(2, "/")
                }
                return sb.toString()
            }

            private fun cleanDate(text: String): String {
                return text.replace("[^0-9/]".toRegex(), "")
            }
        })
        binding.etDate.setText(getObject.date)
        binding.save.setOnClickListener {
            editMoviee()
        }
        return viewBinding!!.root
    }

    private fun editMoviee() {
        val name = binding.etName.text.toString().trim()
        val author = binding.etAuthor.text.toString().trim()
        val definition = binding.etDefinition.text.toString().trim()
        val date = binding.etDate.text.toString().trim()


        if (name.isEmpty()) {
            binding.etName.error = "Place can not be empty!!!"
            return
        }
        if (author.isEmpty()) {
            binding.etAuthor.error = "Place can not be empty!!!"
            return
        }
        if (definition.isEmpty()) {
            binding.etDefinition.error = "Place can not be empty!!!"
            return
        }
        if (date.isEmpty()) {
            binding.etDate.error = "Place can not be empty!!!"
            return
        }
        val data = Movies(positionNumberID, name, author, definition, date)
        viewModel.updateMovies(data)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
}
