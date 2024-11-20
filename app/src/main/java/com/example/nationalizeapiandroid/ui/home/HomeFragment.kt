package com.example.nationalizeapiandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nationalizeapiandroid.adapter.NationalizeAdapter
import com.example.nationalizeapiandroid.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: NationalizeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Ініціалізація RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Ініціалізація адаптера
        adapter = NationalizeAdapter(
            items = emptyList(),
            onEditClick = {}, // Не потрібні кнопки у HomeFragment
            onDeleteClick = {}
        )
        recyclerView.adapter = adapter

        // Спостереження за LiveData
        homeViewModel.result.observe(viewLifecycleOwner, { result ->
            adapter.updateData(listOf(result)) // Оновлюємо адаптер
        })

        homeViewModel.errorMessage.observe(viewLifecycleOwner, { message ->
            showError(message)
        })

        // Налаштування обробки кнопки
        binding.button.setOnClickListener {
            val name = binding.textInput.text.toString().trim()
            if (name.isNotEmpty()) {
                homeViewModel.searchName(name) // Пошук імені
            } else {
                showError("Please enter a name")
            }
        }
        return root
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
