package com.manpro.wibufinders.ui.main.eventdetail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.manpro.wibufinders.databinding.FragmentEventBinding
import com.manpro.wibufinders.factory.ViewModelFactory
import com.manpro.wibufinders.remote.network.response.EventDetail
import com.manpro.wibufinders.remote.network.response.EventResponse


class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private val handler = Handler()
    private lateinit var factory: ViewModelFactory
    private val viewModel: EventViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModelFactory()
        setComponent()
        setAction()
    }

    private fun setComponent() {
        viewModel.getEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.layoutShimmerEvent.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.layoutShimmerEvent.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    handler.postDelayed({
                        binding.layoutShimmerEvent.visibility = View.GONE
                        binding.recyclerivewevents.visibility = View.VISIBLE
                        showEvent(result.data)
                    }, 1000)
                }
            }
        }
    }


    private fun setAction() {
        binding.topNavigation.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_addevent -> {
                    startActivity(Intent(requireActivity(), AddEventFormActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun showEvent(event: EventResponse) {
        recyclerView = binding.recyclerivewevents
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventAdapter = EventAdapter(requireActivity(), event.data.events.reversed())
        recyclerView.adapter = eventAdapter

        eventAdapter.setOnItemClickListener(object : EventAdapter.OnItemClickListener {
            override fun onClick(clickedView: View, event: EventDetail) {
                navigateToDetailEvent(event)
            }
        })
    }

    private fun navigateToDetailEvent(detailEvent: EventDetail) {
        val intent = Intent(requireContext(), EventDetailActivity::class.java)
        intent.putExtra("EXTRA_EVENT", detailEvent)
        startActivity(intent)
    }

    private fun setViewModelFactory() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}