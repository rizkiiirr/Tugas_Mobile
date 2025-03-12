data class KeripikPisang(
    val kodeKeripik: Int,
    var merekKeripik: String?,
    var rasaKeripik: String,
    var stokKeripik: Long,
    var hargaKeripik: Double
)
{
    fun lihatDaftarBarang(){
        println("===================================")
        println("Kode Keripik  : $kodeKeripik")
        println("Merek Keripik : ${merekKeripik ?: "Tidak Ada"}")
        println("Rasa Keripik  : $rasaKeripik")
        println("Stok Keripik  : $stokKeripik")
        println("Harga Keripik : Rp${hargaKeripik.toInt()}")
        println("===================================")
    }
}

class inventoriBarang{
    private val daftarBarang: MutableList<KeripikPisang> = mutableListOf()

    fun lihatDaftarBarang() {
        if (daftarBarang.isEmpty()) {
            println("Stok barang kosong!")
        } else {
            daftarBarang.forEach{it.lihatDaftarBarang()}
        }
    }

    fun tambahDaftarBarang(){
        var kode: Int
        do{
            print("Masukkan kode barang: ")
            kode = readLine()?.toIntOrNull() ?: -1
            if(kode <= 0){
                println("Masukkan kode barang!")
            }
            else if (daftarBarang.any {it.kodeKeripik == kode}){
                println("Kode barang sudah ada, masukkan kode yang berbeda")
                kode = -1
            }
        } while (kode <= 0)

        print("Masukkan merek barang (kosongkan jika tidak ada): ")
        var merek = readLine()?.takeIf {it.isNotBlank()}

        var rasa: String
        do{
            print("Masukkan Rasa Keripik: ")
            rasa = readLine().orEmpty()
            if (rasa.isBlank()) println("Silakan isi rasa keripik!")
        } while (rasa.isBlank())

        var stok: Long
        do {
            print("Masukkan stok barang: ")
            stok = readLine()?.toLongOrNull() ?: -1
            if (stok < 0) println("Masukkan stok barang!")
        } while (stok < 0)

        var harga: Double
        do{
            print("Masukkan harga barang: ")
            harga = readLine()?.toDoubleOrNull() ?: 0.0
            if (harga <= 0) println("Harga barang harus lebih dari nol!")
        } while (harga <= 0)

        daftarBarang.add(KeripikPisang(kode, merek, rasa, stok, harga))
        println("Barang berhasil ditambahkan!")
    }

    fun editDataBarang(){
        var kode: Int?
        do{
            print("Masukkan kode barang: ")
            kode = readLine()?.toIntOrNull()
            if (kode == null || kode <= 0) println("Masukkan kode barang!")
        } while (kode == null || kode <= 0)

        val barang = daftarBarang.find { it.kodeKeripik == kode }
        if (barang != null) {
            print("Masukkan merek barang baru (kosongkan jika tidak): ")
            val merek = readLine()?.takeIf { it.isNotBlank() }

            print("Masukkan rasa keripik baru: ")
            val rasa = readLine()?.takeIf { it.isNotBlank() } ?: barang.rasaKeripik

            var stok: Long
            do {
                print("Masukkan stok barang baru (kosongkan jika tidak): ")
                stok =  readLine()?.takeIf { it.isNotBlank() }?.toLongOrNull() ?: barang.stokKeripik
                if (stok < 0) println("Silakan isi stok barang baru (isi 0 jika tidak ada)!")
            } while (stok < 0)

            var harga: Double
            do {
                print("Masukkan harga barang baru (kosongkan jika tidak): ")
                harga = readLine()?.takeIf { it.isNotBlank() }?.toDoubleOrNull() ?: barang.hargaKeripik
                if (harga < 0) println("Harga barang tidak boleh kurang dari 0!")
            } while (harga < 0)


            barang.merekKeripik = merek ?: barang.merekKeripik
            barang.rasaKeripik = rasa
            barang.stokKeripik = stok
            barang.hargaKeripik = harga

            println("Barang berhasil diperbarui!")
        } else {
            println("Barang dengan kode tersebut tidak ditemukan! Silakan coba lagi.")
            editDataBarang()
        }
    }

    fun hapusDataBarang() {
        var kode: Int
        do {
            print("Masukkan kode barang yang akan dihapus: ")
            kode = readLine()?.toIntOrNull() ?: -1
            if (kode <= 0) println("kode barang harus berupa angka positif!")
        } while (kode <= 0)

        val barang = daftarBarang.find { it.kodeKeripik == kode }
        if (barang != null) {
            print("Apakah Anda yakin ingin menghapus barang ini? (Y/N): ")
            val konfirmasi = readLine()?.trim()?.uppercase()
            if (konfirmasi == "Y") {
                daftarBarang.remove(barang)
                println("Barang berhasil dihapus!")
            } else {
                println("Penghapusan barang dibatalkan.")
            }
        } else {
            println("Barang dengan kode tersebut tidak ditemukan! Silakan coba lagi.")
            hapusDataBarang()
        }
    }
}

fun main() {
    val inventori = inventoriBarang()
    var pilihan: Int

    do {
        println("\nPilih Menu:")
        println("1. Lihat Daftar Barang")
        println("2. Tambah Data Barang")
        println("3. Edit Data Barang")
        println("4. Hapus Data Barang")
        println("5. Tutup")


        pilihan = readValidInt("Input Menu:")
        println()

        when (pilihan) {
            1 -> inventori.lihatDaftarBarang()
            2 -> inventori.tambahDaftarBarang()
            3 -> inventori.editDataBarang()
            4 -> inventori.hapusDataBarang()
            5 -> println("Program selesai. Terima kasih!")
            else -> println("Pilihan tidak valid, silakan coba lagi.\n")
        }
    } while (pilihan != 5)
}

fun readValidInt(prompt: String): Int {
    while (true) {
        print("$prompt ")
        val input = readLine()?.toIntOrNull()
        if (input != null && input > 0) return input
        println("Masukkan angka yang valid!")
    }
}