package com.manpro.wibufinders.ui.main.eventdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manpro.wibufinders.DummyFiles.SocialEventList
import com.manpro.wibufinders.databinding.FragmentSocialeventBinding

class SocialEventFragment : Fragment() {
    private var _binding: FragmentSocialeventBinding? = null
    private val binding get() = _binding!!
    private lateinit var socialEventAdapter: SocialEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialeventBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = binding.recyclerivewevents
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inisialisasi adapter dengan data dari SocialEventList
        socialEventAdapter = SocialEventAdapter(SocialEventList.data)
        recyclerView.adapter = socialEventAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

