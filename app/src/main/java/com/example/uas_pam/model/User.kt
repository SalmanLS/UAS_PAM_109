package com.example.uas_pam.model

data class User(
    val idUser: String,
    val namaUser: String,
    val jeniskUser: String,
    val umurUser: String,
){
    constructor(): this("","","","")
}

