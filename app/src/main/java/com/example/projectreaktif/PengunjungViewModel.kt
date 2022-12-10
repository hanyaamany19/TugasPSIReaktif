package com.example.projectreaktif

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject

class PengunjungViewModel : ViewModel() {
    // Mendeklarasikan variabel untuk menyimpan jumlah pengunjung
    private var jumlah: Int = 0

    // Menghasilkan tipe String supaya bisa ditampilkan langsung pada TextView
    fun getJumlah(): String {
        return jumlah.toString()
    }
    fun tambah(): Unit {
        jumlah += 1
    }
    fun kurang(): Unit {
        jumlah -= 1
    }

    /* VERSI REAKTIF */

    // Mendeklarasikan variabel untuk menyimpan jumlah pengunjung
    private var jumlahReaktif: Int = 0

    // Mendeklarasikan Subject untuk memancarkan jumlah pengunjung
    private val jumlahSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

    // Untuk mengakses Subject dari Activity
    fun getJumlahSubject(): BehaviorSubject<Int> {
        return jumlahSubject
    }

    fun tambahReaktif(): Unit {
        jumlahReaktif += 1
        jumlahSubject.onNext(jumlahReaktif)
    }
    fun kurangReaktif(): Unit {
        jumlahReaktif -= 1
        jumlahSubject.onNext(jumlahReaktif)
    }
}
