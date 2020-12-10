package com.example.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private val db by lazy { FirebaseDatabase.getInstance().getReference("My_NOTIF_ID") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if (!it.isSuccessful){
                Log.e("TOKEN", "error")
                return@OnCompleteListener
            }

            val tokenDevice = it.result
            db.setValue(tokenDevice)

            Log.i("TOKEN", tokenDevice!!)
        })
    }
}