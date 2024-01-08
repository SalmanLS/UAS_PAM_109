package com.example.uas_pam.model

data class Imt(
    val idData: String,
    var idUser: String,
    val bbUser: Long,
    val tbUser: Long,
    var imtClass: String,
){
    constructor() : this("","",0,0,"")
}
