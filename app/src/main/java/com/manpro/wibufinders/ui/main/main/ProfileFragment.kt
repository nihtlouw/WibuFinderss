package com.manpro.wibufinders.ui.main.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.manpro.wibufinders.R
import com.manpro.wibufinders.databinding.FragmentPopupConfirmlogoutBinding
import com.manpro.wibufinders.ui.main.login.LoginActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val logoutButton: Button = view.findViewById(R.id.btn_logout)
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return view
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = FragmentPopupConfirmlogoutBinding.inflate(LayoutInflater.from(context))

        builder.setView(binding.root)

        val dialog = builder.create()

        binding.buttonYeslogout.setOnClickListener {
            // Tambahkan aksi logout di sini
            dialog.dismiss()
            // Arahkan ke LoginActivity
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.buttonNologout.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
