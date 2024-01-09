package com.example.uas_pam.data

import android.content.ContentValues
import android.util.Log
import com.example.uas_pam.model.Imt
import com.example.uas_pam.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await


interface ImtRepository{

    suspend fun save(imt: Imt):String

    suspend fun update(imt: Imt)

    suspend fun deleteBasedOnUserId(userId: String)

    fun getAllWithUser(): Flow<List<Pair<Imt,User?>>>

    fun getAllBasedOnUserId(userId: String): Flow<Pair<Imt,User?>?>

    fun getImtByUserId(userId: String) :Flow<Imt?>
}

class ImtRepositoryImpl(private val firestore: FirebaseFirestore): ImtRepository{
    override suspend fun save(imt: Imt): String {
        return try {
            val documentReference = firestore.collection("Imt").add(imt).await()
            firestore.collection("Imt").document(documentReference.id)
                .set(imt.copy(idData = documentReference.id))
            "Berhasil + ${documentReference.id}"
        }catch (e: Exception){
            Log.w(ContentValues.TAG,"Error adding document",e)
            "Gagal $e"
        }
    }

    override suspend fun update(imt: Imt) {
        firestore.collection("Imt").document(imt.idData).set(imt).await()
    }

    override suspend fun deleteBasedOnUserId(userId: String) {
        val imtSnapshot = firestore.collection("Imt").whereEqualTo("idUser", userId).get().await()
        imtSnapshot.documents.forEach { document ->
            document.reference.delete()
        }

        val userSnapshot = firestore.collection("User").whereEqualTo("idUser", userId).get().await()
        userSnapshot.documents.forEach { document ->
            document.reference.delete()
        }
    }

    override fun getAllWithUser(): Flow<List<Pair<Imt, User?>>> = flow{

            val snapshotImt = firestore.collection("Imt").get().await()
            val listImt = snapshotImt.toObjects(Imt::class.java)

            val snapshotUser = firestore.collection("User").get().await()
            val listUser = snapshotUser.toObjects(User::class.java)

            val listResult = listImt.map { imt -> Pair(imt, listUser.find { it.idUser == imt.idUser }) }
            emit(listResult)
    }.flowOn(Dispatchers.IO)

    override fun getAllBasedOnUserId(userId: String): Flow<Pair<Imt, User?>?> = flow {
        val imtSnapshot = firestore.collection("Imt")
            .whereEqualTo("idUser", userId)
            .get()
            .await()
        val userSnapshot = firestore.collection("User")
            .whereEqualTo("idUser", userId)
            .get()
            .await()

        val imtResult = imtSnapshot.toObjects(Imt::class.java)
        val userResult = userSnapshot.toObjects(User::class.java)

        val imt = imtResult.firstOrNull()
        val user = userResult.firstOrNull()

        val result = if (imt != null) Pair(imt, user) else null

        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getImtByUserId(userId: String): Flow<Imt?> {
        return flow {
            val snapshot = firestore.collection("Imt")
                .whereEqualTo("idUser", userId)
                .get()
                .await()
            val imt = snapshot.documents.firstOrNull()?.toObject(Imt::class.java)
            emit(imt)
        }.flowOn(Dispatchers.IO)
    }


}