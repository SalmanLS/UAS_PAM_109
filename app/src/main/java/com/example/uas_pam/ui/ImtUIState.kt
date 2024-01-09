package com.example.uas_pam.ui

import com.example.uas_pam.model.Imt
import com.example.uas_pam.model.User


data class ImtUIState(
    val detailImt: DetailImt = DetailImt()
)


data class DetailImt(
    val id: String = "",
    val userId: String = "",
    val bb: Long = 0,
    val tb: Long = 0,
    var imt: String = ""
) {
    fun findImt(): Double {
        val bbDouble = bb.toDouble()
        val tbDouble = tb.toDouble() / 100

        return if (tbDouble != 0.0) {
            bbDouble / (tbDouble * tbDouble)
        } else {
            0.0
        }

    }

    fun determineImt(): String {
        val imtValue = findImt()
        return when {
            imtValue < 18.5 -> "Kurus"
            imtValue < 24.9 -> "Normal"
            imtValue < 29.9 -> "Gemuk"
            else -> "Obesitas"
        }
    }
}

fun DetailImt.toImt(): Imt = Imt(
    idData = id,
    idUser = userId,
    bbUser = bb,
    tbUser = tb,
    imtClass = imt
)

data class HomeUIState(
    val alldata: List<AllData> = listOf(),
    val dataLength: Int = 0
)


fun Imt.toDetailImt(): DetailImt =
    DetailImt(
        id = idData,
        userId = idUser,
        bb = bbUser,
        tb = tbUser,
        imt = imtClass
    )


fun Imt.toImtUIState(): ImtUIState = ImtUIState(
    detailImt = this.toDetailImt()
)

data class DetailUIState(
    val allDataUi: AllDataUi = AllDataUi()
)

data class AllDataUi(
    val id: String = "",
    val nama: String = "",
    val jenisk: String = "",
    val umur: String = "",
    val bb: Long = 0,
    val tb: Long = 0,
    val imt: String = ""
)



fun AllData.toAllDataUi(): AllDataUi =
    AllDataUi(
        id = idData,
        nama = namaUser,
        jenisk = jeniskUser,
        umur = umurUser,
        bb = bbUser,
        tb = tbUser,
        imt = imtClass
    )

fun AllDataUi.toAllData(): AllData =
    AllData(
        idData = id,
        namaUser = nama,
        jeniskUser = jenisk,
        umurUser = umur,
        bbUser = bb,
        tbUser = tb,
        imtClass = imt
    )


//untuk menampung data gabungan
data class AllData(
    val idData: String,
    val namaUser: String,
    val jeniskUser: String,
    val umurUser: String,
    val bbUser: Long,
    val tbUser: Long,
    val imtClass: String
) {
    constructor() : this("", "", "", "", 0, 0, "")
}





