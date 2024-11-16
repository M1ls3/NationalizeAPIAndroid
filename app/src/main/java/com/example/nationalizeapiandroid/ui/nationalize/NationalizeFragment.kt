package com.example.nationalizeapiandroid.ui.nationalize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nationalizeapiandroid.databinding.FragmentNationalizeBinding

class NationalizeFragment : Fragment() {
// TODO: show DB and develop Edit and Delete options
    private var _binding: FragmentNationalizeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val nationalizeViewModel =
            ViewModelProvider(this).get(NationalizeViewModel::class.java)

       _binding = FragmentNationalizeBinding.inflate(inflater, container, false)
       val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}