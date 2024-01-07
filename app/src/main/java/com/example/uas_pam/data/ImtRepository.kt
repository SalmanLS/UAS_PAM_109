package com.example.uas_pam.data

import android.content.ContentValues
import android.util.Log
import com.example.uas_pam.model.Imt
import com.example.uas_pam.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await


interface ImtRepository{

    suspend fun save(imt: Imt):String

    suspend fun update(imt: Imt)

    suspend fun delete(imtId: String)

    fun getAll(): Flow<List<Imt>>
}

class ImtRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
): ImtRepository{
    override suspend fun save(imt: Imt): String {
        return try {
            val currentUserId = userRepository.save(User())
            val documentReference = firestore.collection("Imt").add(imt).await()

            firestore.collection("Imt").document(documentReference.id)
                .set(imt.copy(idData = documentReference.id, idUser = currentUserId))
            "Berhasil + ${documentReference.id}"
        }catch (e: Exception){
            Log.w(ContentValues.TAG,"Error adding document",e)
            "Gagal $e"
        }
    }

    override suspend fun update(imt: Imt) {
        firestore.collection("Imt").document(imt.idData).set(imt).await()
    }

    override suspend fun delete(imtId: String) {
        firestore.collection("Imt").document(imtId).delete().await()
    }

    override fun getAll(): Flow<List<Imt>> = flow {
        val snapshot = firestore.collection("Imt")
            .orderBy("imtUser", Query.Direction.ASCENDING)
            .get()
            .await()
        val imt = snapshot.toObjects(Imt::class.java)
        emit(imt)
    }.flowOn(Dispatchers.IO)

}