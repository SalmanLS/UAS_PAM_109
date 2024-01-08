package com.example.uas_pam.data

import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val userRepository: UserRepository
    val imtRepository: ImtRepository
}

class AplikasiContainer: AppContainer{
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(firestore)
    }

    override val imtRepository: ImtRepository by lazy {
        ImtRepositoryImpl(firestore)
    }

}