package com.example.uas_pam.data

import android.content.ContentValues
import android.util.Log
import com.example.uas_pam.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

interface UserRepository {
    fun getAll(): Flow<List<User>>

    suspend fun save(user: User): String

    suspend fun update(user: User)

    suspend fun delete(userId: String)

    fun getUserById(userId: String): Flow<User>

    fun getUserId(): String?
}

class UserRepositoryImpl(private val firestore: FirebaseFirestore) : UserRepository {

    private var currentUserId: String? = null

    override fun getAll(): Flow<List<User>> = flow {
        val snapshot =
            firestore.collection("User")
                .orderBy("namaUser", Query.Direction.ASCENDING)
                .get()
                .await()
        val user = snapshot.toObjects(User::class.java)
        emit(user)
    }.flowOn(Dispatchers.IO)

    override suspend fun save(user: User): String {
        return try {
            val documentReference = firestore.collection("User").add(user).await()

            currentUserId = documentReference.id

            firestore.collection("User").document(documentReference.id).set(user.copy( idUser = documentReference.id ))
            "Berhasil + ${documentReference.id}"
        }catch (e:Exception){
            Log.w(ContentValues.TAG,"Error adding document",e)
            "Gagal $e"
        }
    }

    override suspend fun update(user: User) {
        firestore.collection("User").document(user.idUser).set(user).await()
    }

    override suspend fun delete(userId: String) {
        firestore.collection("User").document(userId).delete().await()
    }

    override fun getUserById(userId: String): Flow<User> {
        return flow {
            val snapshot = firestore.collection("User").document(userId).get().await()
            val user = snapshot.toObject(User::class.java)
            emit(user!!)
        }.flowOn(Dispatchers.IO)
    }

    override fun getUserId(): String? {
        return currentUserId
    }
}