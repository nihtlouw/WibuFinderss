package com.manpro.wibufinders.ui.main.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.manpro.wibufinders.DummyFiles.AnimeFestEventDetail
import com.manpro.wibufinders.DummyFiles.AnimeFestList
import com.manpro.wibufinders.R
import com.manpro.wibufinders.ui.main.eventdetail.EventAnimeDetailActivity
import com.manpro.wibufinders.ui.main.eventdetail.GeneralEventAdapter
import com.manpro.wibufinders.ui.main.eventdetail.GeneralEventViewModel
import com.manpro.wibufinders.ui.main.eventdetail.PopularEventAdapter
import org.threeten.bp.LocalDate

class HomeFragment : Fragment() {

    private var events: MutableList<AnimeFestEventDetail> = AnimeFestList.data.toMutableList()
    private lateinit var recyclerViewGeneral: RecyclerView
    private lateinit var recyclerViewPopular: RecyclerView
    private lateinit var adapterGeneral: GeneralEventAdapter
    private lateinit var viewModel: GeneralEventViewModel
    private lateinit var adapterPopular: PopularEventAdapter
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private var currentItemIndex = 0
    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            getUserLocation { userLocation ->
                // Setelah lokasi diperoleh, lakukan sesuatu
                if (userLocation != null) {
                    // Lakukan sesuatu dengan lokasi yang diperoleh
                } else {
                    // Jika lokasi tidak diperoleh
                    Toast.makeText(requireContext(), "Tidak dapat mengambil lokasi pengguna", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val btnFilter = view.findViewById<AppCompatImageButton>(R.id.btnFilter)
        btnFilter.setOnClickListener {
            showFilterOptions(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        searchView = view.findViewById(R.id.search_view)

        viewModel = ViewModelProvider(this).get(GeneralEventViewModel::class.java)

        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Panggil performSearch hanya saat pengguna menekan enter pada keyboard
                performSearch(query)

                // Sembunyikan keyboard
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Jika teks berubah menjadi kosong, kembalikan data item list ke data default
                if (newText.isEmpty()) {
                    restoreDefaultItemList()
                } else {
                    // Jika teks tidak kosong, panggil performSearch untuk mencari item yang sesuai
                    performSearch(newText)
                }
                return true
            }
        })


        // Inisialisasi RecyclerView untuk event umum
        recyclerViewGeneral = view.findViewById(R.id.rv_generalevent)
        adapterGeneral = GeneralEventAdapter(events) // Menggunakan seluruh daftar events di sini
        recyclerViewGeneral.adapter = adapterGeneral
        recyclerViewGeneral.layoutManager = LinearLayoutManager(requireContext())

        // Observer untuk eventList dari ViewModel
        viewModel.eventList.observe(viewLifecycleOwner, Observer { eventList ->
            // Memanggil prepareEventList() untuk menyesuaikan urutan acara
            adapterGeneral.setData(eventList)
            progressBar.visibility = View.GONE


            // Memperbarui tampilan adapter setelah data diperbarui
            adapterGeneral.notifyDataSetChanged()
        })


        adapterGeneral.setOnItemClickListener(object : GeneralEventAdapter.OnItemClickListener {
            override fun onClick(clickedView: View, event: AnimeFestEventDetail) {
                if (event.eventDate < LocalDate.now()) {
                    val intent = Intent(requireContext(), EventAnimeDetailActivity::class.java)
                    intent.putExtra("event", event)
                    startActivity(intent)
                    val context = clickedView.context
                    Toast.makeText(context, "Event closed", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(requireContext(), EventAnimeDetailActivity::class.java)
                    intent.putExtra("event", event)
                    startActivity(intent)
                }
            }
        })


        // Inisialisasi RecyclerView untuk event populer
        recyclerViewPopular = view.findViewById(R.id.listpopularevent)
        adapterPopular = PopularEventAdapter(events)
        recyclerViewPopular.adapter = adapterPopular
        recyclerViewPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Attach PagerSnapHelper setelah recyclerViewPopular diinisialisasi
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewPopular)

        // Auto scroll RecyclerView populer
        startAutoScroll()

        adapterPopular.setOnItemClickListener(object : PopularEventAdapter.OnItemClickListener {
            override fun onClick(clickedView: View, event: AnimeFestEventDetail) {
                val intent = Intent(requireContext(), EventAnimeDetailActivity::class.java)
                intent.putExtra("event", event)
                startActivity(intent)
            }
        })

        loadDataFromViewModel()


        return view
    }

    private fun loadDataFromViewModel() {
        // Ambil data dari ViewModel dan perbarui adapter
        viewModel.eventList.observe(viewLifecycleOwner, Observer { eventList ->
            adapterGeneral.setData(eventList)
        })

        // Ambil data acara dari ViewModel saat fragment pertama kali dibuat
        viewModel.setSearchResults(AnimeFestList.data)
    }

    private fun startAutoScroll() {
        autoScrollHandler.postDelayed(object : Runnable {
            override fun run() {
                val nextItemIndex = (currentItemIndex + 1) % events.size
                val smoothScroller = object : LinearSmoothScroller(requireContext()) {
                    override fun getVerticalSnapPreference(): Int {
                        return SNAP_TO_START
                    }

                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return 0.5f // Atur kecepatan scroll di sini
                    }
                }
                smoothScroller.targetPosition = nextItemIndex
                recyclerViewPopular.layoutManager?.startSmoothScroll(smoothScroller)
                currentItemIndex = nextItemIndex
                autoScrollHandler.postDelayed(this, 2000) // Jeda 2 detik
            }
        }, 4000) // Jeda 2 detik
    }

    private fun performSearch(query: String) {
        // Tampilkan ProgressBar
        progressBar.visibility = View.VISIBLE

        // Batalkan pencarian sebelumnya jika ada
        searchRunnable?.let { runnable ->
            searchHandler.removeCallbacks(runnable)
        }

        // Buat Runnable baru untuk melakukan pencarian setelah delay
        searchRunnable = Runnable {
            val searchResults = searchData(query)
            Log.d("HomeFragment", "Search Results: $searchResults") // Cek hasil pencarian
            viewModel.setSearchResults(searchResults)

            // Sembunyikan ProgressBar setelah pencarian selesai
            progressBar.visibility = View.GONE
        }

        // Jalankan pencarian setelah delay 2 detik
        searchHandler.postDelayed(searchRunnable!!, 1000)
    }


    private fun searchData(query: String): List<AnimeFestEventDetail> {
        val searchResults = events.filter { event ->
            event.eventName.contains(query, ignoreCase = true)
        }
        if (searchResults.isEmpty()) {
            // Tampilkan pesan event tidak ditemukan jika hasil pencarian kosong
            Toast.makeText(requireContext(), "Event tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
        return searchResults
    }


    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Fungsi untuk meminta izin lokasi jika belum diberikan
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    // Override untuk menangani respons pengguna terhadap permintaan izin
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Izin diberikan, dapatkan lokasi pengguna
                    getUserLocation { userLocation ->
                        // Setelah lokasi diperoleh, lakukan sesuatu
                        if (userLocation != null) {
                            // Lakukan sesuatu dengan lokasi yang diperoleh
                        } else {
                            // Jika lokasi tidak diperoleh
                            Toast.makeText(requireContext(), "Tidak dapat mengambil lokasi pengguna", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Izin ditolak, berikan pesan atau lakukan tindakan lainnya
                    Toast.makeText(requireContext(), "Izin lokasi ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
    }
    // Fungsi untuk mengembalikan data item list ke data default
    private fun restoreDefaultItemList() {
        // Reset adapter dengan data default
        adapterGeneral.setData(AnimeFestList.data)
    }
    private fun showFilterOptions(anchorView: View) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_filter_date -> {
                    filterByDate()
                    true
                }
                R.id.menu_filter_location -> {
                    filterByLocation() // Panggil filterByLocation saat opsi filter lokasi dipilih
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }



    private fun filterByDate() {
        val currentDate = LocalDate.now()
        adapterGeneral.filterEventsByDate(currentDate)
        adapterGeneral.setData(adapterGeneral.eventList)
        viewModel.filterEventsByDate(currentDate)

    }

    private fun filterByLocation() {
        getUserLocation { userLocation ->
            if (userLocation != null) {
                adapterGeneral.filterEventsByLocation(userLocation)
                adapterGeneral.setData(adapterGeneral.eventList)
                viewModel.filterEventsByLocation(userLocation)

            } else {
                requestLocationPermission()
            }
        }
    }



    private fun getUserLocation(callback: (Location?) -> Unit) {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object :
                LocationListener {
                override fun onLocationChanged(location: Location) {
                    // Menyimpan lokasi pengguna
                    callback(location)
                    // Menghentikan pembaruan lokasi setelah ditemukan
                    locationManager.removeUpdates(this)
                }

                override fun onProviderDisabled(provider: String) {
                    // Jika GPS dinonaktifkan
                    callback(null)
                }

                override fun onProviderEnabled(provider: String) {
                    // Jika GPS diaktifkan
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    // Status perubahan
                }
            })
        } else {
            callback(null) // Permission not granted, return null
        }
    }

}
