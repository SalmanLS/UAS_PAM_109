package com.example.uas_pam.ui

import com.example.uas_pam.model.User


data class UserUIState(
    val detailUser: DetailUser = DetailUser()
)

data class DetailUser(
    val id: String = "",
    val nama: String = "",
    val jenisk: String = "",
    val umur: String = ""
)

fun DetailUser.toUser(): User = User(
    idUser = id,
    namaUser = nama,
    jeniskUser = jenisk,
    umurUser = umur
)

fun User.toDetailUser(): DetailUser = DetailUser(
    id = idUser,
    nama = namaUser,
    jenisk = jeniskUser,
    umur = umurUser
)

