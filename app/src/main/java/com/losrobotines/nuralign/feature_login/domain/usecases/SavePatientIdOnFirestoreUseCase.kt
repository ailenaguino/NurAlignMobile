package com.losrobotines.nuralign.feature_login.domain.usecases

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SavePatientIdOnFirestoreUseCase @Inject constructor() {

    operator fun invoke(id: Short) {
        val firebaseUser = Firebase.auth.currentUser
        if (firebaseUser != null) {
            var uid: String
            firebaseUser.let { uid = it.uid }
            val doc = Firebase.firestore.collection("users").document(uid)

            val user = hashMapOf(
                "id" to id.toInt()
            )
            doc.set(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        }
    }
}