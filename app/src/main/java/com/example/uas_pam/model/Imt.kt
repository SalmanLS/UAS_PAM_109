package com.example.uas_pam.model

data class Imt(
    val idData: String,
    val idUser: String,
    val bbUser: Long,
    val tbUser: Long,
    val imtClass: String,
){
    constructor() : this("","",0,0,"")
}
