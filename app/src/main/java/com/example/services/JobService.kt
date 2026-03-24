package com.example.services

import android.app.job.JobParameters
import android.content.Intent
import android.os.Build
import android.os.PersistableBundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobService : android.app.job.JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scope.launch {
                var workItem = params?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)

                    for (i in 1..5) {
                        delay(1000)
                        log("Timer $i  page: $page")
                    }

                    params?.completeWork(workItem)
                    workItem = params?.dequeueWork()
                }
                jobFinished(params, false)
            }
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        scope.cancel()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    private fun log(message: String) {
        Log.i("JobService", message)
    }

    companion object {
        const val JOB_ID = 111
        private const val PAGE = "page"

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }
}

