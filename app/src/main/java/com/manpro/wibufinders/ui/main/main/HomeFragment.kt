package com.manpro.wibufinders.ui.main.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.manpro.wibufinders.R
import com.manpro.wibufinders.model.AnimeEventModel

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var events: MutableList<AnimeEventModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi Firebase Realtime Database
        database = FirebaseDatabase.getInstance().reference.child("AnimeEvent")

        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText) // Update pencarian saat teks berubah
                return true
            }
        })

        return view
    }

    override fun onStart() {
        super.onStart()
        // Tambahkan listener untuk mendengarkan perubahan data di Firebase Realtime Database
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                events.clear()
                for (eventSnapshot in dataSnapshot.children) {
                    val event = eventSnapshot.getValue(AnimeEventModel::class.java)
                    event?.let { events.add(it) }
                }
                // Tampilkan data yang diperoleh di aplikasi
                displaySearchResults(events)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Penanganan kesalahan jika pembacaan data gagal
                Log.e("HomeFragment", "Database error: ${databaseError.message}")
            }
        })
    }

    private fun performSearch(query: String) {
        val searchResults = searchData(query)
        displaySearchResults(searchResults)
    }

    private fun searchData(query: String): List<AnimeEventModel> {
        return events.filter { event ->
            event.eventName?.contains(query, ignoreCase = true) == true ||
                    event.description?.contains(query, ignoreCase = true) == true
        }
    }


    private fun displaySearchResults(searchResults: List<AnimeEventModel>) {
        val containerLayout = view?.findViewById<LinearLayout>(R.id.shimmer_regions)
        containerLayout?.removeAllViews()

        for (event in searchResults) {
            val cardView = createCardViewForEvent(event)
            containerLayout?.addView(cardView)
        }
    }

    private fun createCardViewForEvent(event: AnimeEventModel): View {
        val cardView = layoutInflater.inflate(R.layout.popular_event_list_item, null)
        val titleTextView = cardView.findViewById<TextView>(R.id.title_eventpopular)
        val contentTextView = cardView.findViewById<TextView>(R.id.date_eventpopular)

        titleTextView.text = event.eventName
        contentTextView.text = event.description

        return cardView
    }
}
