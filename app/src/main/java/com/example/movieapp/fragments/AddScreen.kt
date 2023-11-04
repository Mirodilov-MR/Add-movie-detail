package com.example.movieapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.MovieViewModel
import com.example.movieapp.databinding.FragmentAddScreenBinding
import com.example.movieapp.room.Movies

class AddScreen : Fragment() {
    private lateinit var binding: FragmentAddScreenBinding
    private val viewModel: MovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.save.setOnClickListener {
            addScreen()
        }
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
    }

    private fun addScreen() {
        val name = binding.etName.text.toString().trim()
        val author = binding.etAuthor.text.toString().trim()
        val definition = binding.etDefinition.text.toString().trim()
        val date = binding.etDate.text.toString().trim()
        if (name.isEmpty()) {
            binding.etName.error = "Please enter a name"
            return
        }
        if (author.isEmpty()) {
            binding.etAuthor.error = "Please enter an author"
            return
        }
        if (definition.isEmpty()) {
            binding.etDefinition.error = "Please enter a definition"
            return
        }
        if (date.isEmpty()) {
            binding.etDate.error = "Please enter a date"
            return
        }
        val cleanedDate = date.replace("[^0-9]".toRegex(), "")
        datee(cleanedDate,null,name,author,definition)
//        val formattedDate = if (cleanedDate.length == 8) {}
//            val day = cleanedDate.substring(0, 2)
//            val month = cleanedDate.substring(2, 4)
//            val year = cleanedDate.substring(4, 8)
//            val dayInt = day as Int
//            if (dayInt<20)
//            {
//                Toast.makeText(context, "20 dan kichik", Toast.LENGTH_SHORT).show()
//            }
//            else
//            {
//                Toast.makeText(context, "20 dan katta", Toast.LENGTH_SHORT).show()
//
//            }
//            "$day/$month/$year"
//        } else {
//            date
//        }
//        val data = Movies(null, name, author, definition, formattedDate)
//        viewModel.addMovies(data)
//        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
//        findNavController().popBackStack()
    }

    fun datee(
        cleanedDate: String,
        nothing: Nothing?,
        name: String,
        author: String,
        definition: String
    ) {
        if (cleanedDate.length == 8) {
            val day = cleanedDate.substring(0, 2)
            val month = cleanedDate.substring(2, 4)
            val year = cleanedDate.substring(4, 8)
                Toast.makeText(context, "20 dan kichik", Toast.LENGTH_SHORT).show()
                val data = Movies(null, name, author, definition,"$day/$month/$year"
                )
                viewModel.addMovies(data)
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
        }
    }
}
