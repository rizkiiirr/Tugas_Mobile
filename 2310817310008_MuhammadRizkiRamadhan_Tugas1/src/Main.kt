data class Pakaian(
    val idBarang: Int,
    var merekBarang: String?,
    var namaBarang: String,
    var jumlahBarang: Long,
    var hargaBarang: Double
) {

  //  - Pada properti `jumlah`, Anda menggunakan setter kustom untuk mencegah nilai-nilai yang kurang dari atau sama dengan nol. Namun, jika setter menerima nilai yang tidak valid, field `jumlah` tetap menggunakan nilai sebelumnya tanpa memberikan indikasi yang jelas kepada pemanggil fungsi.
  // - Perintah `field` dalam blok `run` akan mengembalikan nilai yang **tidak berubah** sekalipun nilai baru tidak valid, namun pengguna tidak mendapatkan feedback bahwa nilai aslinya tidak diperbarui.

    /*  var jumlah: Long = jumlahBarang
          set(value) {
              field = value.takeIf {it > 0} ?: run {
                  println("Jumlah barang tidak bisa nol atau negatif")
                  field
              }
          }*/

    var jumlah: Long = jumlahBarang
        set(value) {
            if (value > 0) {
                field = value
            } else {
                println("Jumlah barang tidak bisa nol atau negatif")
                // Anda bisa melempar exception jika lebih baik untuk aplikasi Anda.
            }
        }

    fun lihatDaftarBarang() {
        println("===================================")
        println("ID Barang     : $idBarang")
        println("Merek Barang  : ${merekBarang ?: "Tidak Ada"}")
        println("Nama Barang   : $namaBarang")
        println("Jumlah Barang : $jumlah")
        println("Harga Barang  : Rp${hargaBarang.toInt()}")
        println("===================================")
    }
}

class Inventori {
    private val daftarBarang: MutableList<Pakaian> = mutableListOf()

    // Menampilkan daftar barang
    fun lihatDaftarBarang() {
        if (daftarBarang.isEmpty()) {
            println("Tidak ada barang di inventori.")
        } else {
            daftarBarang.forEach { it.lihatDaftarBarang() }
        }
    }

    // Menambahkan barang ke dalam daftar
    fun tambahDataBarang() {
        var id: Int
        do {
            print("Masukkan ID Barang: ")
            id = readLine()?.toIntOrNull() ?: -1
            if (id <= 0) {
                println("ID Barang harus berupa angka positif!")
            } else if (daftarBarang.any { it.idBarang == id }) {
                println("ID Barang sudah ada dalam data, silakan masukkan ID yang berbeda!")
                id = -1
            }
        } while (id <= 0)

       /* println("Masukkan ID Barang:")
        val id = readLine()?.toIntOrNull() ?: 0
        println("Masukkan Merek Barang (kosongkan jika tidak ada):")
        val merek = readLine()?.takeIf { !it.isNullOrBlank() }
        println("Masukkan Nama Barang:")
        val nama = readLine()?.takeIf { it.isNotBlank() } ?: "Nama Tidak Diketahui"
        //  val merek = readLine()?.takeIf { it.isNotBlank() }
        println("Masukkan Jumlah Barang:")
        val jumlah = readLine()?.toLongOrNull() ?: 0L
        // val jumlah = readLongInput()
        println("Masukkan Harga Barang:")
        val harga = readLine()?.toDoubleOrNull() ?: 0.0
        // val harga = readDoubleInput()*/

        print("Masukkan Merek Barang (kosongkan jika tidak ada): ")
        var merek = readLine()?.takeIf { it.isNotBlank() }

        var nama: String
        do {
            print("Masukkan Nama Barang: ")
            nama = readLine().orEmpty()
            if (nama.isBlank()) println("Nama Barang tidak boleh kosong!")
        } while (nama.isBlank())

        var jumlah: Long
        do {
            print("Masukkan Jumlah Barang: ")
            jumlah = readLine()?.toLongOrNull() ?: -1
            if (jumlah <= 0) println("Jumlah Barang harus lebih dari nol!")
        } while (jumlah <= 0)

        var harga: Double
        do {
            print("Masukkan Harga Barang: ")
            harga = readLine()?.toDoubleOrNull() ?: -1.0
            if (harga <= 0) println("Harga Barang harus lebih dari nol!")
        } while (harga <= 0)

        daftarBarang.add(Pakaian(id, merek, nama, jumlah, harga))
        println("Barang berhasil ditambahkan!")
    }

    // Fungsi placeholder untuk mengedit barang
    fun editDataBarang() {
        var id: Int?
        do {
            print("Masukkan ID Barang yang akan diedit: ")
            id = readLine()?.toIntOrNull()
            if (id == null || id < 0) println("ID Barang harus berupa angka positif!")
        } while (id == null || id <= 0)

        val barang = daftarBarang.find { it.idBarang == id }
        if (barang != null) {
            print("Masukkan Merek Barang baru (kosongkan jika tidak ingin mengubah): ")
            val merek = readLine()?.takeIf { it.isNotBlank() }
            print("Masukkan Nama Barang baru: ")
            val nama = readLine()?.takeIf { it.isNotBlank() } ?: barang.namaBarang
            var jumlah: Long
            do {
                print("Masukkan Jumlah Barang baru: ")
                jumlah = readLine()?.toLongOrNull() ?: -1
                if (jumlah <= 0) println("Jumlah Barang harus lebih dari nol!")
            } while (jumlah <= 0)
            var harga: Double
            do {
                print("Masukkan Harga Barang baru: ")
                harga = readLine()?.toDoubleOrNull() ?: -1.0
                if (harga <= 0) println("Harga Barang harus lebih dari nol!")
            } while (harga <= 0)

            barang.merekBarang = merek ?: barang.merekBarang
            barang.namaBarang = nama
            barang.jumlahBarang = jumlah
            barang.hargaBarang = harga

            println("Barang berhasil diperbarui!")
        } else {
            println("Barang dengan ID tersebut tidak ditemukan! Silakan coba lagi.")
            editDataBarang()
        }
    }

    // Fungsi placeholder untuk menghapus barang
    fun hapusDataBarang() {
        var id: Int
        do {
            print("Masukkan ID Barang yang akan dihapus: ")
            id = readLine()?.toIntOrNull() ?: -1
            if (id <= 0) println("ID Barang harus berupa angka positif!")
        } while (id <= 0)

        val barang = daftarBarang.find { it.idBarang == id }
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
            println("Barang dengan ID tersebut tidak ditemukan! Silakan coba lagi.")
            hapusDataBarang()
        }
    }
    }


// Fungsi utama
fun main() {
    val inventori = Inventori()
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

       /* print("Input Menu: ")
        pilihan = readIntInput()
        println()*/

        when (pilihan) {
            1 -> inventori.lihatDaftarBarang()
            2 -> inventori.tambahDataBarang()
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

// Fungsi untuk membaca input Integer dengan validasi
/*fun readIntInput(): Int {
    return try {
        readLine()?.toInt() ?: 0
    } catch (e: NumberFormatException) {
        println("Masukkan angka yang valid!")
        0
    }
}*/
