package com.example.uas_pam

import android.app.Application
import com.example.uas_pam.data.AplikasiContainer
import com.example.uas_pam.data.AppContainer

class ImtApplication : Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AplikasiContainer()
    }
}