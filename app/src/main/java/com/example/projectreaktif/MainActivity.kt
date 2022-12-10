package com.example.projectreaktif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class MainActivity : AppCompatActivity() {
    // Mendeklarasikan objek CompositeDisposable untuk mempermudah men-dispose para subscriber
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* TANPA VIEW MODEL */

        // Mendeklarasikan variabel untuk menyimpan jumlah pengunjung (tanpa ViewModel)
        var jumlah = 0;

        // Mendeklarasikan TextView untuk tampilan tanpa ViewModel
        var tvJumlahTanpaVM = findViewById<TextView>(R.id.jumlah_tanpa_vm)
        tvJumlahTanpaVM.setText(jumlah.toString())

        /* DENGAN VIEW MODEL */

        // Mendeklarasikan ViewModel
        val model: PengunjungViewModel by viewModels()

        // Mendeklarasikan TextView untuk tampilan dengan ViewModel
        var tvJumlahDenganVM = findViewById<TextView>(R.id.jumlah_dengan_vm)
        tvJumlahDenganVM.setText(model.getJumlah())


        /* REAKTIF */

        // Mendeklarasikan TextView untuk tampilan Reaktif
        var tvJumlahReaktif = findViewById<TextView>(R.id.jumlah_reaktif)

        // Berlangganan setiap terjadi perubahan jumlah pengunjung dan bereaksi dengan mengubah nilai pada TextView
        model.getJumlahSubject().subscribe {
            tvJumlahReaktif.setText(it.toString())
        }.addTo(disposable)


        /* OPEARSI UMUM UNTUK PENAMBAHAN DAN PENGURANGAN */

        findViewById<Button>(R.id.btn_tambah).setOnClickListener {
            // Operasi tanpa ViewModel
            jumlah += 1
            tvJumlahTanpaVM.setText(jumlah.toString())

            // Operasi dengan ViewModel
            model.tambah()
            tvJumlahDenganVM.setText(model.getJumlah())

            // Operasi reaktif
            model.tambahReaktif()
        }

        findViewById<Button>(R.id.btn_kurang).setOnClickListener {
            // Operasi tanpa ViewModel
            jumlah -= 1
            tvJumlahTanpaVM.setText(jumlah.toString())

            // Operasi dengan ViewModel
            model.kurang()
            tvJumlahDenganVM.setText(model.getJumlah())

            // Operasi reaktif
            model.kurangReaktif()
        }


    }

    override fun onDestroy() {
        // Pastikan setelah selesai semua subscriber di-dispose agar tidak terjadi leak memory
        disposable.dispose()
        super.onDestroy()
    }
}