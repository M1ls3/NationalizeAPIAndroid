package com.example.nationalizeapiandroid.ui.nationalize

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nationalizeapiandroid.R
import com.example.nationalizeapiandroid.adapter.NationalizeAdapter
import com.example.nationalizeapiandroid.dao.model.CountryEntity
import com.example.nationalizeapiandroid.data.NationalizeWithCountries
import com.example.nationalizeapiandroid.databinding.FragmentNationalizeBinding
import com.example.nationalizeapiandroid.factory.NationalizeViewModelFactory

class NationalizeFragment : Fragment() {

    private var _binding: FragmentNationalizeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NationalizeViewModel
    private lateinit var adapter: NationalizeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNationalizeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Ініціалізуємо ViewModel з фабрикою
        val application = requireNotNull(this.activity).application
        val factory = NationalizeViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(NationalizeViewModel::class.java)

        // Ініціалізуємо RecyclerView
        val recyclerView = binding.fragmentRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Ініціалізуємо адаптер і зв'язуємо з RecyclerView
        adapter = NationalizeAdapter(
            items = emptyList(), // Порожній список, поки не отримані дані
            onEditClick = { item ->
                showEditDialog(item)
            },
            onDeleteClick = { item ->
                viewModel.deleteNationalize(item.nationalize)
            }
        )

        recyclerView.adapter = adapter

        // viewModel.simpleData()

        // Спостерігаємо за змінами в LiveData з ViewModel
        viewModel.nationalizeItems.observe(viewLifecycleOwner, { items ->
            adapter.updateData(items)
        })

        // Завантажуємо дані після створення фрагмента
        viewModel.loadItems()

        return root
    }

    // Метод для відображення діалогу редагування
    private fun showEditDialog(item: NationalizeWithCountries) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit, null)

        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextName)
        val countEditText = dialogView.findViewById<EditText>(R.id.editCount)

        // Заповнюємо поля з поточними значеннями
        nameEditText.setText(item.nationalize.name)
        countEditText.setText(item.nationalize.count.toString())

        // Заповнюємо поля країн
        val countryEditTexts = listOf(
            dialogView.findViewById<EditText>(R.id.editCountry1),
            dialogView.findViewById<EditText>(R.id.editCountry2),
            dialogView.findViewById<EditText>(R.id.editCountry3),
            dialogView.findViewById<EditText>(R.id.editCountry4),
            dialogView.findViewById<EditText>(R.id.editCountry5)
        )

        val probabilityEditTexts = listOf(
            dialogView.findViewById<EditText>(R.id.editProbability1),
            dialogView.findViewById<EditText>(R.id.editProbability2),
            dialogView.findViewById<EditText>(R.id.editProbability3),
            dialogView.findViewById<EditText>(R.id.editProbability4),
            dialogView.findViewById<EditText>(R.id.editProbability5)
        )

        // Заповнюємо поля інформацією про країни
        for (i in item.countries.indices) {
            countryEditTexts[i].setText(item.countries[i].country_id)
            probabilityEditTexts[i].setText(item.countries[i].probability.toString())
        }

        dialogBuilder.setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val updatedName = nameEditText.text.toString()
                val updatedCount = countEditText.text.toString().toIntOrNull() ?: 0

                val updatedCountries = mutableListOf<CountryEntity>()
                for (i in countryEditTexts.indices) {
                    val countryId = countryEditTexts[i].text.toString()
                    val probability = probabilityEditTexts[i].text.toString().toDoubleOrNull() ?: 0.0

                    if (countryId.isNotEmpty()) {
                        updatedCountries.add(CountryEntity(
                            nationalize_id = item.nationalize.id,
                            country_id = countryId,
                            probability = probability
                        ))
                    }
                }

                // Оновлюємо Nationalize та Country
                val updatedNationalize = item.nationalize.copy(name = updatedName, count = updatedCount)
                viewModel.updateNationalize(updatedNationalize, updatedCountries)

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

