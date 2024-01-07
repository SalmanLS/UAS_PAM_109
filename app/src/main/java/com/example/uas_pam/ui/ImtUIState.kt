package com.example.uas_pam.ui

import com.example.uas_pam.model.Imt


data class ImtUIState(
    val detailImt: DetailImt = DetailImt()
)


data class DetailImt(
    val id: String = "",
    val userId: String = "",
    val bb: Long = 0,
    val tb: Long = 0,
    val imt: String = ""
){
    fun findImt(): Double{
        val bbDouble = bb.toDouble()
        val tbDouble = tb.toDouble() / 100

        return if (tbDouble != 0.0){
            bbDouble / (tbDouble * tbDouble)
        } else{
            0.0
        }

    }

    fun determineImt(): String{
        val imtValue = findImt()
        return when {
            imtValue < 18.5 -> "Underweight"
            imtValue < 24.9 -> "Normal Weight"
            imtValue < 29.9 -> "Overweight"
            else -> "Obese"
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

fun Imt.toDetailImt(): DetailImt = DetailImt(
    id = idData,
    userId = idUser,
    bb = bbUser,
    tb = tbUser,
    imt = imtClass
)