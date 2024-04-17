package com.manpro.wibufinders.ui.main.eventdetail

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.manpro.wibufinders.R
import com.manpro.wibufinders.databinding.ActivityAddEventFormBinding
import com.manpro.wibufinders.factory.ViewModelFactory
import com.manpro.wibufinders.model.CreateEventModel
import com.manpro.wibufinders.ui.main.main.MainActivity
import com.manpro.wibufinders.utils.showToast
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEventFormActivity : AppCompatActivity() {

    private lateinit var ivBannerImage: ImageView
    private lateinit var etDateDay: EditText
    private lateinit var etEventTimeStart: EditText
    private lateinit var etEventTimeEnd: EditText
    private lateinit var binding: ActivityAddEventFormBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var radioGroup: RadioGroup
    private lateinit var etTicketPrice: EditText
    private lateinit var cardview4: MaterialCardView
    private lateinit var tvTicketprice: TextView
    private var loadingPopup: PopupWindow? = null
    private var currentImageUri: Uri? = null
    private val viewModel: AddEventFormViewModel by viewModels { factory }


    companion object {
        private const val REQUEST_CODE_PERMISSION = 123
        private const val REQUEST_CODE_PICK_IMAGE = 124
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event_form)
        binding = ActivityAddEventFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEventNameInput()
        setViewModelFactory()
        setupCharacterLimits()
        setupTicketPriceInput()
        setupEventLocationInput()
        setupOrganizerInput()

        val backButton = findViewById<MaterialButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val confirmButton = findViewById<Button>(R.id.btn_confirm)
        confirmButton.setOnClickListener {
            if (isEventDataComplete()) {
                showConfirmationDialog()
            } else {
                Toast.makeText(this, "Anda belum melengkapi data event!", Toast.LENGTH_SHORT).show()
            }
        }

        val ivCalender = findViewById<ImageView>(R.id.iv_calender)
        ivCalender.setOnClickListener {
            showDatePickerDialog()
        }
        ivBannerImage = findViewById(R.id.iv_bannerimage)
        etDateDay = findViewById(R.id.et_dateday)
        etDateDay.setOnClickListener {
            showDatePickerDialog()
        }

        etDateDay.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()
            }
        }

        etEventTimeStart = findViewById(R.id.et_eventtime_start)
        etEventTimeStart.inputType = InputType.TYPE_NULL
        etEventTimeStart.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showTimePickerDialog(etEventTimeStart)
            }
        }

        etEventTimeEnd = findViewById(R.id.et_eventtime_end)
        etEventTimeEnd.inputType = InputType.TYPE_NULL
        etEventTimeEnd.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showTimePickerDialog(etEventTimeEnd)
            }
        }

        val cardViewBanner = findViewById<MaterialCardView>(R.id.cardview_10)
        cardViewBanner.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        radioGroup = findViewById(R.id.radioGroup)
        etTicketPrice = findViewById(R.id.et_ticketprice)
        cardview4 = findViewById(R.id.cardview_4)
        tvTicketprice = findViewById(R.id.tv_ticketprice)

        cardview4.visibility = View.GONE
        etTicketPrice.visibility = View.GONE
        tvTicketprice.visibility = View.GONE
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_opened) {
                etTicketPrice.setText("0")
                etTicketPrice.isEnabled = false
                etTicketPrice.visibility = View.GONE
                cardview4.visibility = View.GONE
                tvTicketprice.visibility = View.GONE
            } else {
                etTicketPrice.setText("")
                etTicketPrice.isEnabled = true
                etTicketPrice.visibility = View.VISIBLE
                cardview4.visibility = View.VISIBLE
                tvTicketprice.visibility = View.VISIBLE
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            pickImage()
        } else {
            Toast.makeText(this, "Permission to access gallery denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickImage() {
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickIntent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            ivBannerImage.setImageURI(imageUri)
        }
    }

    private fun sendDataToApi() {
        val eventName = findViewById<EditText>(R.id.et_eventname).text.toString()
        val eventDateDay = findViewById<EditText>(R.id.et_dateday).text.toString()
        val eventDescription = findViewById<EditText>(R.id.et_descevent).text.toString()
        val ticketPrice = findViewById<EditText>(R.id.et_ticketprice).text.toString()
        val contactPerson = findViewById<EditText>(R.id.et_contactperson).text.toString()
        val organizer = findViewById<EditText>(R.id.et_organizer).text.toString()
        val eventLocation = findViewById<EditText>(R.id.et_eventlocation).text.toString()
        val eventTimeStart = etEventTimeStart.text.toString()
        val eventTimeEnd = etEventTimeEnd.text.toString()
        val formattedStartTime = formatTimeWithAmPm(eventTimeStart)
        val formattedEndTime = formatTimeWithAmPm(eventTimeEnd)
        val image = imageViewToBase64(findViewById(R.id.iv_bannerimage))
        Log.d("IMAGE", image.take(10))

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        Log.d("HAHAAHH", checkedRadioButtonId.toString())

        if (checkedRadioButtonId != -1) {
            viewModel.createEvent(
                CreateEventModel(
                    nama = eventName,
                    waktu = eventDateDay,
                    description = eventDescription,
                    jenis = checkedRadioButtonId==2131362368,
                    harga = if (ticketPrice=="") "0" else ticketPrice,
                    contact = contactPerson,
                    penyelenggara = organizer,
                    lokasi = eventLocation,
                    mulai = formattedStartTime,
                    selesai = formattedEndTime,
                    gambar = image,
                )
            ).observe(this){ result ->
                when (result) {
                    is Result.Loading -> {
                        showLoadingDialog(true)
                    }
                    is Result.Error -> {
                        showLoadingDialog(false)
                        showCustomDialog(result.data, false)
                    }
                    is Result.Success -> {
                        showLoadingDialog(false)
                        showCustomDialog(result.data.message, true)
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("from_page", 3)
                            startActivity(intent)
                            finish()
                        }, 2000)
                    }
                }
            }
        } else {
            showToast(this@AddEventFormActivity, "Please Choose Event Type")
        }
    }


    fun imageViewToBase64(imageView: ImageView): String {
        val bitmap: Bitmap = imageView.drawable.toBitmap()
        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        val byteArray = byteArrayOutputStream.toByteArray()
        return "data:image/png;base64,${Base64.encodeToString(byteArray, Base64.NO_WRAP)}"
    }

    private fun showDatePickerDialog() {
        etDateDay.isEnabled = false
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }

                val indonesiaLocale = Locale("id", "ID")

                val formattedDate = SimpleDateFormat("EEEE, dd MMMM yyyy", indonesiaLocale)
                    .format(selectedCalendar.time)

                etDateDay.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.setOnCancelListener {
            etDateDay.isEnabled = true
        }

        datePickerDialog.show()
        etDateDay.inputType = InputType.TYPE_NULL
    }


    private fun showTimePickerDialog(editText: EditText) {
        editText.isEnabled = false
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val formattedTime =
                    String.format(Locale("id", "ID"), "%02d:%02d", selectedHour, selectedMinute)
                editText.setText(formattedTime)
                editText.isEnabled = true
            },
            hour,
            minute,
            false
        )

        timePickerDialog.setOnCancelListener {
            editText.isEnabled = true
        }

        timePickerDialog.show()
    }

    private fun isEventDataComplete(): Boolean {
        val eventName = findViewById<EditText>(R.id.et_eventname).text.toString()
        val eventDateDay = findViewById<EditText>(R.id.et_dateday).text.toString()
        val eventDescription = findViewById<EditText>(R.id.et_descevent).text.toString()
        val ticketPrice = findViewById<EditText>(R.id.et_ticketprice).text.toString()
        val contactPerson = findViewById<EditText>(R.id.et_contactperson).text.toString()
        val organizer = findViewById<EditText>(R.id.et_organizer).text.toString()
        val eventLocation = findViewById<EditText>(R.id.et_eventlocation).text.toString()
        val eventTimeStart = etEventTimeStart.text.toString()
        val eventTimeEnd = etEventTimeEnd.text.toString()

        return eventName.isNotEmpty() &&
                eventDateDay.isNotEmpty() &&
                eventDescription.isNotEmpty() &&
                ticketPrice.isNotEmpty() &&
                organizer.isNotEmpty() &&
                eventLocation.isNotEmpty() &&
                eventTimeStart.isNotEmpty() &&
                eventTimeEnd.isNotEmpty()
    }

    private fun showConfirmationDialog() {
        val popupView = layoutInflater.inflate(R.layout.fragment_popup_confirmevent, null)

        // Find views in the custom layout
        val tvAskConfirm: TextView = popupView.findViewById(R.id.tv_askconfirm)
        val tvAskDesc: TextView = popupView.findViewById(R.id.tv_askdesc)
        val btnNoConfirm: Button = popupView.findViewById(R.id.button_noconfirm)
        val btnYesConfirm: Button = popupView.findViewById(R.id.button_yesconfirm)

        // Set confirmation message and description
        tvAskConfirm.text = "Are you sure?"
        tvAskDesc.text = "We will inform your event if it fulfills requirements at the notification"

        // Create a PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        // Set background drawable with a transparent color
        popupWindow.setBackgroundDrawable(resources.getDrawable(android.R.color.transparent))

        // Show the PopupWindow at the center of the screen
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        // Set click listeners for the buttons
        btnNoConfirm.setOnClickListener {
            popupWindow.dismiss()
        }

        btnYesConfirm.setOnClickListener {
            popupWindow.dismiss()
            sendDataToApi()
        }
    }

    private fun setViewModelFactory() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun showLoadingDialog(play: Boolean = true) {
        if (play) {
            // Show loading popup
            if (loadingPopup == null) {
                val loadingView = layoutInflater.inflate(R.layout.scan_loading, null)
                loadingPopup = PopupWindow(
                    loadingView,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                loadingPopup?.setBackgroundDrawable(resources.getDrawable(android.R.color.transparent))
                loadingPopup?.isOutsideTouchable = false
                loadingPopup?.isFocusable = true
            }
            loadingPopup?.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
        } else {
            // Dismiss loading popup
            loadingPopup?.dismiss()
        }
    }

    private fun showCustomDialog(message: String, success: Boolean) {
        val popupView = layoutInflater.inflate(R.layout.fragment_popup_loggedin, null)

        // Find views in the custom layout
        val messageTextView: TextView = popupView.findViewById(R.id.tv_loggedin)
        val checkListImageView: ImageFilterView = popupView.findViewById(R.id.iv_checklist)
        val cancelImageView: ImageFilterView = popupView.findViewById(R.id.iv_cancel)

        // Set message and visibility based on success
        messageTextView.text = message
        if (success) {
            checkListImageView.visibility = View.VISIBLE
            cancelImageView.visibility = View.GONE
        } else {
            checkListImageView.visibility = View.GONE
            cancelImageView.visibility = View.VISIBLE
        }

        // Create a PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        // Set background drawable with a transparent color
        popupWindow.setBackgroundDrawable(resources.getDrawable(android.R.color.transparent))

        // Show the PopupWindow at the center of the screen
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        // Dismiss the PopupWindow after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            popupWindow.dismiss()
        }, 2000)
    }
    private fun formatTimeWithAmPm(time: String): String {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        try {
            val date = inputFormat.parse(time)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return time
        }
    }
    private fun setupCharacterLimits() {
        val etContactPerson = findViewById<EditText>(R.id.et_contactperson)
        val etDescEvent = findViewById<EditText>(R.id.et_descevent)

        val minDescEventLength = 250 // Jumlah karakter minimum yang diinginkan
        val maxDescEventLength = 700 // Jumlah karakter maksimum yang diinginkan
        val maxContactPersonLength = 60 // Jumlah karakter maksimum untuk contact person

        etContactPerson.filters = arrayOf(InputFilter.LengthFilter(maxContactPersonLength))
        etDescEvent.filters = arrayOf(InputFilter.LengthFilter(maxDescEventLength))

        etDescEvent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val length = s?.length ?: 0
                if (length < minDescEventLength) {
                    etDescEvent.error = "Deskripsi event harus memiliki setidaknya $minDescEventLength karakter."
                } else if (length > maxDescEventLength) {
                    etDescEvent.error = "Deskripsi event tidak boleh melebihi $maxDescEventLength karakter."
                } else {
                    etDescEvent.error = null
                }
            }
        })
    }
    private fun setupTicketPriceInput() {
        val etTicketPrice = findViewById<EditText>(R.id.et_ticketprice)

        etTicketPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                etTicketPrice.removeTextChangedListener(this)

                val text = s.toString()

                val cleanedText = text.replace("[^\\d]".toRegex(), "")

                val formattedText = if (cleanedText.isNotEmpty()) "Rp $cleanedText" else ""

                etTicketPrice.setText(formattedText)
                etTicketPrice.setSelection(formattedText.length) // Posisikan kursor di akhir

                etTicketPrice.addTextChangedListener(this)
            }
        })
    }
    private fun setupEventLocationInput() {
        val etEventLocation = findViewById<EditText>(R.id.et_eventlocation)
        val maxEventLocationLength = 40

        val filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxEventLocationLength))
        etEventLocation.filters = filters
    }
    private fun setupOrganizerInput() {
        val etOrganizer = findViewById<EditText>(R.id.et_organizer)
        val maxOrganizerLength = 58

        val filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxOrganizerLength))
        etOrganizer.filters = filters
    }
    private fun setupEventNameInput() {
        val etEventName = findViewById<EditText>(R.id.et_eventname)
        val maxEventNameLength = 51

        val filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxEventNameLength))
        etEventName.filters = filters
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast(this@AddEventFormActivity, "No Media Selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivBannerimage.setPadding(0, 0, 0, 0)
            binding.ivBannerimage.setImageURI(it)
            binding.ivBannerimage.visibility = View.VISIBLE
        }
    }
}

