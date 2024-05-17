package com.manpro.wibufinders.ui.main.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.manpro.wibufinders.R
import com.manpro.wibufinders.ui.main.login.LoginActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lakukan inisialisasi dan pengaturan fungsi-fungsi yang seharusnya ada di sini
        // Misalnya, atur onClickListener untuk tombol-tombolnya
        val btnFavorite: Button = view.findViewById(R.id.btn_favorite)
        val btnLogout: Button = view.findViewById(R.id.btn_logout)

        btnFavorite.setOnClickListener {
            // Tambahkan logika untuk tombol favorite di sini
        }

        btnLogout.setOnClickListener {
            // Membuat intent untuk memulai LoginActivity
            val intent = Intent(activity, LoginActivity::class.java)

            // Menghapus semua aktivitas sebelumnya dari tumpukan aktivitas sehingga pengguna tidak dapat kembali ke layar profil setelah logout
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Memulai LoginActivity
            startActivity(intent)

            // Mengakhiri sesi pengguna atau melakukan tindakan logout lainnya di sini
        }

    }
}
