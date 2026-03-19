package com.example.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        val message = intent?.getIntExtra(EXTRA_TEXT, 21) ?: 0
        serviceScope.launch {
            for (i in message + 1 .. message + 100){
                delay(1000)
                log("Timer $i")
            }
        }
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        log("onDestroy")
        serviceScope.cancel()
        super.onDestroy()
    }

    private fun log(message: String) {
        Log.i("MyService", message)
    }

    companion object {
        private const val EXTRA_TEXT = "start"
        fun newIntent(context: Context, message: Int): Intent{
            return Intent(context, MyService::class.java).apply {
                putExtra(EXTRA_TEXT, message)
            }
        }
    }
}

